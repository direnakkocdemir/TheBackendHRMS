package com.CCT.HRMS.business.concretes;

import java.time.LocalDate;
import java.util.List;

import com.CCT.HRMS.business.abstracts.AdvertisementService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.core.Utilities.BusinessRules;
import com.CCT.HRMS.dataAccess.abstracts.AdvertisementDao;
import com.CCT.HRMS.dataAccess.abstracts.ApplicationDao;
import com.CCT.HRMS.dataAccess.abstracts.LocationDao;
import com.CCT.HRMS.dataAccess.abstracts.WorkTimeDao;
import com.CCT.HRMS.dataAccess.abstracts.Users.EmployerDao;
import com.CCT.HRMS.dataAccess.abstracts.Users.JobseekerDao;
import com.CCT.HRMS.entities.DTOs.AdvertisementDto;
import com.CCT.HRMS.entities.concretes.Advertisement;
import com.CCT.HRMS.entities.concretes.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Business layer to manage the Advertisement
 * 
 * @author diren
 *
 */

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class AdvertisementManager implements AdvertisementService {

    // Properties
    private AdvertisementDao advertisementDao;
    private EmployerDao employerDao;
    private JobseekerDao jobseekerDao;
    private ApplicationDao applicationDao;
    private LocationDao locationDao;
    private WorkTimeDao workTimeDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public AdvertisementManager(AdvertisementDao advertisementDao, JobseekerDao jobseekerDao,
            ApplicationDao applicationDao, EmployerDao employerDao, LocationDao locationDao, WorkTimeDao workTimeDao) {
        this.advertisementDao = advertisementDao;
        this.jobseekerDao = jobseekerDao;
        this.applicationDao = applicationDao;
        this.employerDao = employerDao;
        this.locationDao = locationDao;
        this.workTimeDao = workTimeDao;
    }

    /**
     * Adding an Advertisement to the database
     */
    @Override
    public Result add(AdvertisementDto advertisementDto) {
        var result = BusinessRules.run(checkEmployerExist(advertisementDto.getEmployerId()),
                checkLocationIsValid(advertisementDto.getLocationId()),
                checkWorkTimeIsValid(advertisementDto.getWorkTimeId()));
        if (result.isSuccess()) {
            Advertisement advertisement = new Advertisement();
            advertisement.setEmployer(employerDao.getById(advertisementDto.getEmployerId()));
            advertisement.setJobTitle(advertisementDto.getJobTitle());
            advertisement.setDescription(advertisementDto.getDescription());
            advertisement.setLocation(locationDao.getById(advertisementDto.getLocationId()));
            advertisement.setOpenPositions(advertisementDto.getOpenPosition());
            advertisement.setWorkTime(workTimeDao.getById(advertisementDto.getWorkTimeId()));
            advertisement.setActive(true);
            advertisement.setCreateDate(LocalDate.now());
            advertisementDao.save(advertisement);
            return new SuccessResult(Messages.JobAdAdded);
        }
        return new ErrorResult(result.getMessage());
    }

    /**
     * Deleting an Advertisement from the database
     */
    @Override
    public Result delete(int id) {
        Advertisement advertisementToDelete = advertisementDao.getById(id);
        if (advertisementToDelete != null) {
            advertisementDao.delete(advertisementToDelete);
            return new SuccessResult(Messages.AdvertisementDeleted);
        }
        return new ErrorResult(Messages.AdvertisementNotFound);
    }

    /**
     * Updating Advertisement information from the database
     */
    @Override
    public Result update(Advertisement advertisement) {
        Result result = BusinessRules.run(checkAdvertisementExist(advertisement.getId()),
                checkAdvertisementActive(advertisement.getId()));
        if (result.isSuccess()) {
            Advertisement jobAdToUpdate = advertisementDao.getById(advertisement.getId());
            jobAdToUpdate.setJobTitle(advertisement.getJobTitle());
            jobAdToUpdate.setLocation(advertisement.getLocation());
            jobAdToUpdate.setOpenPositions(advertisement.getOpenPositions());
            jobAdToUpdate.setWorkTime(advertisement.getWorkTime());
            jobAdToUpdate.setDescription(advertisement.getDescription());
            jobAdToUpdate.setActive(advertisement.isActive());
            advertisementDao.save(jobAdToUpdate);
            return new SuccessResult(Messages.JobAdUpdated);
        }
        return new ErrorResult(result.getMessage());
    }

    /**
     * Closing the Advertisement to not applying
     */
    @Override
    public Result closeAdvertisement(int id) {
        Result result = BusinessRules.run(checkAdvertisementExist(id), checkAdvertisementActive(id));
        if (result.isSuccess()) {
            Advertisement advertisementToClose = advertisementDao.getById(id);
            advertisementToClose.setActive(false);
            advertisementDao.save(advertisementToClose);
            return new SuccessResult(Messages.AdvertisementClosed);
        }
        return new ErrorResult(result.getMessage());
    }

    /**
     * Getting all the Advertisements from the database
     */
    @Override
    public DataResult<List<Advertisement>> getAll() {
        List<Advertisement> jobAds = advertisementDao.findAll();
        return new SuccessDataResult<>(jobAds);
    }

    /**
     * Getting all the Advertisements by id from the database
     */
    @Override
    public DataResult<Advertisement> getJobAdById(int id) {
        Advertisement jobAd = advertisementDao.findById(id);
        if (jobAd != null) {
            return new SuccessDataResult<>(jobAd);
        }
        return new ErrorDataResult<>();
    }

    /**
     * Getting all the Advertisements by employerId from the database
     */
    @Override
    public DataResult<List<Advertisement>> getJobAdsByEmployerId(int id) {
        List<Advertisement> jobAds = advertisementDao.getByEmployer_Id(id);
        if (jobAds.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(jobAds);
    }

    /**
     * Getting all the Advertisements by companyName from the database
     */
    @Override
    public DataResult<List<Advertisement>> getJobAdsByEmployerName(String name, int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        List<Advertisement> jobAds = advertisementDao.getByEmployer_CompanyName(name, pageable).getContent();
        if (jobAds.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(jobAds);
    }

    /**
     * Applying to an Advertisement by a jobseeker
     */
    @Override
    public Result application(int advertisementId, int jobseekerId) {
        Result result = BusinessRules.run(checkJobseekerExist(jobseekerId),
                checkJobseekerAppliedBefore(advertisementId, jobseekerId), checkAdvertisementActive(advertisementId));
        if (result.isSuccess()) {
            Application application = new Application();
            application.setAdvertisement(advertisementDao.findById(advertisementId));
            application.setJobseeker(jobseekerDao.findById(jobseekerId));
            applicationDao.save(application);
            return new SuccessResult(Messages.SuccessfullyApplied);
        }
        return new ErrorResult(result.getMessage());
    }

    /**
     * Getting all Advertisements page by page
     */
    @Override
    public DataResult<List<Advertisement>> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Advertisement> advertisements = advertisementDao.findAll(pageable).getContent();
        return new SuccessDataResult<List<Advertisement>>(advertisements);
    }

    /**
     * Getting all Advertisements by jobTitle
     */
    @Override
    public DataResult<List<Advertisement>> getJobAdsByJobTitle(String jobTitle, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Advertisement> jobAds = advertisementDao.getByJobTitleContainsIgnoreCase(jobTitle, pageable).getContent();
        if (jobAds.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(jobAds);
    }

    /**
     * Getting all Advertisements by location
     */
    @Override
    public DataResult<List<Advertisement>> getJobAdsByLocation(int location, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Advertisement> jobAds = advertisementDao.getByLocation_Id(location, pageable).getContent();
        if (jobAds.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(jobAds);
    }

    /**
     * Getting all Advertisements by jobTitle and by location
     */
    @Override
    public DataResult<List<Advertisement>> getJobAdsByJobTitleAndLocation(String jobTitle, int location, int pageNo,
            int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Advertisement> jobAds = advertisementDao
                .getByJobTitleContainsIgnoreCaseAndLocation_Id(jobTitle, location, pageable).getContent();
        if (jobAds.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(jobAds);
    }

    /**
     * Getting all Advertisements by jobTitle, by location and by workTime
     */
    @Override
    public DataResult<List<Advertisement>> getJobAdsByJobTitleAndLocationAndWorkTime(String jobTitle, int location,
            int workTime, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Advertisement> jobAds = advertisementDao
                .getByJobTitleContainsIgnoreCaseAndLocation_IdAndWorkTime_Id(jobTitle, location, workTime, pageable)
                .getContent();
        if (jobAds.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(jobAds);
    }

    /**
     * Getting all Advertisements by location and by workTime
     */
    @Override
    public DataResult<List<Advertisement>> getJobAdsByLocationAndWorkTime(int location, int workTime, int pageNo,
            int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Advertisement> jobAds = advertisementDao.getByLocation_IdAndWorkTime_Id(location, workTime, pageable)
                .getContent();
        if (jobAds.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(jobAds);
    }

    /**
     * Getting all Advertisements by jobTitle and by workTime
     */
    @Override
    public DataResult<List<Advertisement>> getJobAdsByJobTitleAndWorkTime(String jobTitle, int workTime, int pageNo,
            int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Advertisement> jobAds = advertisementDao
                .getByJobTitleContainsIgnoreCaseAndWorkTime_Id(jobTitle, workTime, pageable).getContent();
        if (jobAds.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(jobAds);
    }

    /**
     * Getting all Advertisements by workTime
     */
    @Override
    public DataResult<List<Advertisement>> getJobAdsByWorkTime(int workTimeId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Advertisement> jobAds = advertisementDao.getByWorkTime_Id(workTimeId, pageable).getContent();
        if (jobAds.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(jobAds);
    }

    /**
     * Getting all Advertisements by id
     */
    @Override
    public DataResult<Advertisement> getById(int adId) {
        Advertisement advertisement = advertisementDao.getById(adId);
        return new SuccessDataResult<>(advertisement);
    }

    // Validations

    private Result checkAdvertisementExist(int id) {

        if (advertisementDao.existsById(id)) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.AdvertisementNotExist);
    }

    private Result checkAdvertisementActive(int id) {

        if (advertisementDao.findById(id).isActive()) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.AdvertisementNotActive);
    }

    private Result checkLocationIsValid(int locationId) {

        if (locationDao.existsById(locationId)) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.LocaationNotExist);
    }

    private Result checkWorkTimeIsValid(int workTimeId) {

        if (workTimeDao.existsById(workTimeId)) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.WorkTimeNotExist);
    }

    private Result checkEmployerExist(int employerId) {

        if (employerDao.existsById(employerId)) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.EmployerNotExist);
    }

    private Result checkJobseekerExist(int jobseekerId) {

        if (jobseekerDao.existsById(jobseekerId)) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.JobseekerNotExist);
    }

    private Result checkJobseekerAppliedBefore(int advertisementId, int jobseekerId) {

        List<Application> applicantions = applicationDao.getByAdvertisement_Id(advertisementId);

        for (Application application : applicantions) {
            if (application.getJobseeker().getId() == jobseekerId) {
                return new ErrorResult(Messages.JobseekerAlreadyApplied);
            }
        }
        return new SuccessResult();
    }
}

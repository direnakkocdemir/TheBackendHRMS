package com.CCT.HRMS.business.concretes.Users;

import java.util.List;

import com.CCT.HRMS.business.abstracts.Users.JobseekerService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.dataAccess.abstracts.Users.JobseekerDao;
import com.CCT.HRMS.entities.DTOs.JobseekerInfoDto;
import com.CCT.HRMS.entities.concretes.Users.Jobseeker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business layer to manage the Jobseeker
 * 
 * @author diren
 *
 */

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class JobseekerManager implements JobseekerService {

    // Properties
    private JobseekerDao jobseekerDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public JobseekerManager(JobseekerDao jobseekerDao) {
        this.jobseekerDao = jobseekerDao;
    }

    /**
     * Adding a Jobseeker to the database
     */
    @Override
    public Result add(Jobseeker jobseeker) {
        jobseekerDao.save(jobseeker);
        return new SuccessResult(Messages.JobseekerAdded);
    }

    /**
     * Deleting a Jobseeker from the database
     */
    @Override
    public Result delete(Jobseeker jobseeker) {
        Jobseeker jobseekerToDelete = jobseekerDao.getById(jobseeker.getId());
        jobseekerDao.delete(jobseekerToDelete);
        return new SuccessResult(Messages.JobseekerDeleted);
    }

    /**
     * Updating a Jobseeker information from the database
     */
    @Override
    public Result update(Jobseeker jobseeker) {
        Jobseeker jobseekerToUpdate = jobseekerDao.getById(jobseeker.getId());
        jobseekerToUpdate.setFirstName(jobseeker.getFirstName());
        jobseekerToUpdate.setLastName(jobseeker.getLastName());
        jobseekerToUpdate.setDateOfBirth(jobseeker.getDateOfBirth());
        jobseekerToUpdate.setEmail(jobseeker.getEmail());
        jobseekerToUpdate.setPassword(jobseeker.getPassword());
        return new SuccessResult(Messages.JobseekerUpdated);
    }

    /**
     * Getting all the jobseekers from database
     */
    @Override
    public DataResult<List<Jobseeker>> getAll() {
        List<Jobseeker> jobseekers = jobseekerDao.findAll();
        return new SuccessDataResult<List<Jobseeker>>(jobseekers, Messages.JobseekersListed);
    }

    /**
     * Getting the defined jobseeker by id
     */
    @Override
    public DataResult<Jobseeker> getJobseekerById(int id) {
        Jobseeker jobseeker = jobseekerDao.getById(id);
        if (jobseeker != null) {
            return new SuccessDataResult<Jobseeker>(jobseeker);
        }
        return new ErrorDataResult<Jobseeker>(Messages.JobseekerNotFound);
    }
    /**
     * Checking a Jobseeker whether exist or not by id
     */
    @Override
    public Result existsById(int id) {
        if (jobseekerDao.getById(id) != null) {
            return new SuccessResult();
        }
        return new ErrorResult();
    }
    /**
     * Getting a Jobseeker detail information by id
     */
    @Override
    public DataResult<JobseekerInfoDto> getJobseekerInfoById(int id) {
        Jobseeker jobseeker = jobseekerDao.getById(id);
        JobseekerInfoDto jobseekerInfoDto = new JobseekerInfoDto();
        jobseekerInfoDto.setEmail(jobseeker.getEmail());
        jobseekerInfoDto.setFirstName(jobseeker.getFirstName());
        jobseekerInfoDto.setLastName(jobseeker.getLastName());
        jobseekerInfoDto.setDateOfBirth(jobseeker.getDateOfBirth());
        jobseekerInfoDto.setJobTitle(jobseeker.getJobTitle());
        return new SuccessDataResult<>(jobseekerInfoDto);
    }

}

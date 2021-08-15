package com.CCT.HRMS.business.concretes.Users;

import java.util.List;

import com.CCT.HRMS.business.abstracts.Users.EmployerService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.dataAccess.abstracts.Users.EmployerDao;
import com.CCT.HRMS.entities.DTOs.EmployerInfoDto;
import com.CCT.HRMS.entities.concretes.Users.Employer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business layer to manage the Employer
 * 
 * @author diren
 *
 */

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class EmployerManager implements EmployerService {

    // Properties
    private EmployerDao employerDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public EmployerManager(EmployerDao employerDao) {
        this.employerDao = employerDao;
    }

    /**
     * Adding an Employer to the database
     */
    @Override
    public Result add(Employer employer) {
        this.employerDao.save(employer);
        return new SuccessResult(Messages.EmployerAdded);
    }

    /**
     * Deleting an Employer from the database
     */
    @Override
    public Result delete(Employer employer) {
        Employer employerToDelete = employerDao.getById(employer.getId());
        this.employerDao.delete(employerToDelete);
        return new SuccessResult(Messages.EmployerDeleted);
    }

    /**
     * Updating an Employer information from the database
     */
    @Override
    public Result update(Employer employer) {
        Employer employerToUpdate = employerDao.getById(employer.getId());
        employerToUpdate.setCompanyName(employer.getCompanyName());
        employerToUpdate.setEmail(employer.getEmail());
        employerToUpdate.setPhone(employer.getPhone());
        employerToUpdate.setWebsite(employer.getWebsite());
        employerToUpdate.setPassword(employer.getPassword());
        employerToUpdate.setPublishedYear(employer.getPublishedYear());
        employerToUpdate.setIndustry(employer.getIndustry());
        this.employerDao.saveAndFlush(employerToUpdate);
        return new SuccessResult(Messages.EmployerUpdated);
    }

    /**
     * Getting all the Employers from the database
     */
    @Override
    public DataResult<List<Employer>> getAll() {
        List<Employer> employers = this.employerDao.findAll();
        return new SuccessDataResult<List<Employer>>(employers, Messages.EmployersAreListed);
    }

    /**
     * Getting the defined Employer by id
     */
    @Override
    public DataResult<Employer> getEmployerById(int id) {
        Employer employer = employerDao.getById(id);
        if (employer != null) {
            return new SuccessDataResult<Employer>(employer);
        }
        return new ErrorDataResult<Employer>(Messages.EmployerNotFound);
    }
    /**
     * Checking an Employer whether exist or not by id
     */
    @Override
    public Result existsById(int id) {
        if (employerDao.getById(id) != null) {
            return new SuccessResult();
        }
        return new ErrorResult();
    }
    /**
     * Getting an Employer detail information by id
     */
    @Override
    public DataResult<EmployerInfoDto> getEmployerInfoById(int id) {
        Employer employer = employerDao.getById(id);
        EmployerInfoDto employerInfoDto = new EmployerInfoDto();
        employerInfoDto.setCompanyName(employer.getCompanyName());
        employerInfoDto.setWebsite(employer.getWebsite());
        employerInfoDto.setPhone(employer.getPhone());
        employerInfoDto.setEmail(employer.getEmail());
        employerInfoDto.setPublishedYear(employer.getPublishedYear());
        employerInfoDto.setIndustry(employer.getIndustry());
        return new SuccessDataResult<>(employerInfoDto);
    }
}

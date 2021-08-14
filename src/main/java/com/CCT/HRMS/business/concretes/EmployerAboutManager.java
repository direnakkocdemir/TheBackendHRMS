package com.CCT.HRMS.business.concretes;

import java.util.List;

import com.CCT.HRMS.business.abstracts.EmployerAboutService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.core.Utilities.BusinessRules;
import com.CCT.HRMS.dataAccess.abstracts.EmployerAboutDao;
import com.CCT.HRMS.dataAccess.abstracts.Users.EmployerDao;
import com.CCT.HRMS.entities.DTOs.EmployerAboutDto;
import com.CCT.HRMS.entities.concretes.EmployerAbout;
import com.CCT.HRMS.entities.concretes.Users.Employer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class EmployerAboutManager implements EmployerAboutService {

    // Properties
    private EmployerAboutDao employerAboutDao;
    private EmployerDao employerDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public EmployerAboutManager(EmployerAboutDao employerAboutDao, EmployerDao employerDao) {
        this.employerAboutDao = employerAboutDao;
        this.employerDao = employerDao;
    }

    /**
     * Adding an EmployerAbout to the database
     */
    @Override
    public Result add(EmployerAboutDto employerAboutDto) {
        Result result = BusinessRules.run(checkEmployerExist(employerAboutDto.getEmployerId()),
                checkEmployerHasAbout(employerAboutDto.getEmployerId()));
        if (result.isSuccess()) {
            EmployerAbout employerAbout = new EmployerAbout();
            employerAbout.setEmployer(employerDao.getById(employerAboutDto.getEmployerId()));
            employerAbout.setAbout(employerAboutDto.getAbout());
            employerAboutDao.save(employerAbout);
            return new SuccessResult(Messages.AboutAdded);
        }
        return new ErrorResult(result.getMessage());
    }

     @Override
     public Result delete(int id) {
    	 EmployerAbout employerAboutToDelete = employerAboutDao.getById(id);
    	 if(employerAboutToDelete!=null) {
    		 this.employerAboutDao.delete(employerAboutToDelete);
    		 return new SuccessResult(Messages.AboutDeleted);
    	 }
    	 return new ErrorResult(Messages.AboutNotFound);
     }

    // @Override
    // public Result update(EmployerAbout employerAbout) {
    // // TODO Auto-generated method stub
    // return null;
    // }

    /**
     * Getting all the EmployerAbout information from the database
     */
    @Override
    public DataResult<List<EmployerAbout>> getAll() {
        List<EmployerAbout> abouts = employerAboutDao.findAll();
        if (abouts.isEmpty()) {
            return new ErrorDataResult<List<EmployerAbout>>(Messages.AboutsAreNotFound);
        }
        return new SuccessDataResult<List<EmployerAbout>>(abouts);
    }

    /**
     * Getting all the EmployerAbout information by employerId from the database
     */
    @Override
    public DataResult<List<EmployerAbout>> getEmployerAboutByEmployerId(int id) {
        List<EmployerAbout> abouts = employerAboutDao.getEmployerAboutByEmployerId(id);
        return new SuccessDataResult<>(abouts);
    }

    // @Override
    // public DataResult<EmployerAbout> getEmployerAboutById(int id) {
    // // TODO Auto-generated method stub
    // return null;
    // }

    // Validation

    private Result checkEmployerExist(int id) {

        if (employerDao.existsById(id)) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.EmployerNotExist);
    }

    private Result checkEmployerHasAbout(int id) {
        Employer employer = employerDao.getById(id);
        if (employer.getEmployerAbout() == null) {
            return new SuccessResult();
        } else {
            EmployerAbout employerAboutToDelete = employer.getEmployerAbout();
            this.employerAboutDao.delete(employerAboutToDelete);
            return new SuccessResult();
        }
    }
}

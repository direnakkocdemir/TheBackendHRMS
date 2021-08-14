package com.CCT.HRMS.business.concretes;

import java.util.List;

import com.CCT.HRMS.business.abstracts.ApplicationService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.dataAccess.abstracts.ApplicationDao;
import com.CCT.HRMS.entities.concretes.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class ApplicationManager implements ApplicationService {
    
    // Properties
    private ApplicationDao applicationDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public ApplicationManager(ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }
    /**
     * Adding an Application to the database
     */
    @Override
    public Result add(Application application) {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * Getting all the Applications from the database
     */
    @Override
    public DataResult<List<Application>> getAll() {
        List<Application> applications = applicationDao.findAll();
        if (applications.isEmpty()) {
            return new ErrorDataResult<>(Messages.ApplicationsAreNotFound);
        }
        return new SuccessDataResult<>(applications);
    }
    /**
     * Getting all the Applications by advertisemntId from the database
     */
    @Override
    public DataResult<List<Application>> getAllByAdvertisementId(int id) {
        List<Application> applications = applicationDao.getByAdvertisement_Id(id);
        if (applications.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(applications);
    }
    /**
     * Getting all the Applications by jobseekerId from the database
     */
    @Override
    public DataResult<List<Application>> getAllByJobseekerId(int id) {
        List<Application> applications = applicationDao.getByJobseeker_Id(id);
        return new SuccessDataResult<>(applications);
    }

}

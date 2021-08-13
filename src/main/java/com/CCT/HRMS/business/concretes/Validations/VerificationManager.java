package com.CCT.HRMS.business.concretes.Validations;

import java.util.List;

import com.CCT.HRMS.business.abstracts.Validations.VerificationService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.dataAccess.abstracts.Validations.VerificationDao;
import com.CCT.HRMS.entities.concretes.Validations.Verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class VerificationManager implements VerificationService {

    //Repositories
    private VerificationDao verificationDao;

    @Autowired
    public VerificationManager(VerificationDao verificationDao) {
        this.verificationDao = verificationDao;
    }

    @Override
    public Result add(Verification verification) {
        verificationDao.save(verification);
        return new SuccessResult();
    }

    @Override
    public Result delete(Verification verification) {
        Verification verificationToDelete = verificationDao.getById(verification.getId());
        verificationDao.delete(verificationToDelete);
        return new SuccessResult();
    }

    @Override
    public Result update(Verification verification) {
        Verification verificationToUpdate = verificationDao.getById(verification.getId());
        verificationToUpdate.setUserId(verification.getUserId());
        verificationToUpdate.setCode(verification.getCode());
        verificationToUpdate.setCreatedAt(verification.getCreatedAt());
        verificationToUpdate.setVerified(verification.isVerified());
        return null;
    }

    @Override
    public DataResult<List<Verification>> getAll() {
        List<Verification> verifications = verificationDao.findAll();
        if(verifications.isEmpty()){
            return new ErrorDataResult<List<Verification>>(Messages.VerificationsNotFound);
        }
        return new SuccessDataResult<List<Verification>>(verifications);
    }

    @Override
    public DataResult<Verification> getById(int id) {
        Verification verification = verificationDao.getById(id);
        if(verification!=null){
            return new SuccessDataResult<Verification>(verification);
        }
        return new ErrorDataResult<Verification>(verification);
    }

    
}

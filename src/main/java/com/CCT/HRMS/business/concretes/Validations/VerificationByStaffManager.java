package com.CCT.HRMS.business.concretes.Validations;

import java.util.List;

import com.CCT.HRMS.business.abstracts.Validations.VerificationByStaffService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.dataAccess.abstracts.Validations.VerificationByStaffDao;
import com.CCT.HRMS.entities.concretes.Validations.VerificationByStaff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class VerificationByStaffManager implements VerificationByStaffService {

    //Repositories
    private VerificationByStaffDao verificationByStaffDao;

    @Autowired
    public VerificationByStaffManager(VerificationByStaffDao verificationByStaffDao) {
        this.verificationByStaffDao = verificationByStaffDao;
    }


    @Override
    public Result add(VerificationByStaff verificationByStaff) {
        verificationByStaffDao.save(verificationByStaff);
        return new SuccessResult();
    }

    @Override
    public Result delete(VerificationByStaff verificationByStaff) {
        VerificationByStaff verificationByStaffToDelete = verificationByStaffDao.getById(verificationByStaff.getId());
        verificationByStaffDao.delete(verificationByStaffToDelete);
        return new SuccessResult();
    }

    @Override
    public Result update(VerificationByStaff verificationByStaff) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataResult<List<VerificationByStaff>> getAll() {
        List<VerificationByStaff> verificationByStaffs = verificationByStaffDao.findAll();
        if(verificationByStaffs.isEmpty()){
            return new ErrorDataResult<>(Messages.VerificationsNotFound);
        }
        return null;
    }

    @Override
    public DataResult<VerificationByStaff> getById(int id) {
        VerificationByStaff verificationByStaff = verificationByStaffDao.getById(id);
        if(verificationByStaff!=null){
            return new SuccessDataResult<>(verificationByStaff);
        }
        return new ErrorDataResult<>();
    }
    
}

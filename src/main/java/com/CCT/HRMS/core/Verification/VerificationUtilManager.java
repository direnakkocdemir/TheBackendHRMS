package com.CCT.HRMS.core.Verification;

import java.time.LocalDate;
import java.util.UUID;

import com.CCT.HRMS.entities.concretes.Users.User;
import com.CCT.HRMS.entities.concretes.Validations.Verification;
import com.CCT.HRMS.entities.concretes.Validations.VerificationByStaff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CCT.HRMS.business.abstracts.Validations.VerificationByStaffService;
import com.CCT.HRMS.business.abstracts.Validations.VerificationService;
import com.CCT.HRMS.core.Results.*;
@Service
public class VerificationUtilManager implements VerificationUtilService{

    private VerificationService verificationService;
    private VerificationByStaffService verificationByStaffService;
    
    @Autowired
    public VerificationUtilManager(VerificationService verificationService,
            VerificationByStaffService verificationByStaffService) {
        this.verificationService = verificationService;
        this.verificationByStaffService = verificationByStaffService;
    }

    @Override
    public Result verificationByEmail(User user) {
        
        String email = user.getEmail();
        UUID code = UUID.randomUUID();
        System.out.println("Verification code : " + code);
        System.out.println("Verification mail has been sent to :" +email);
        Verification verification = new Verification(user.getId(),code.toString(),false);
        verificationService.add(verification);
        return new SuccessResult();
    }

    @Override
    public Result verificationByStaff(User user) {
        VerificationByStaff verificationByStaff = new VerificationByStaff(user.getId(),false,LocalDate.now());
        verificationByStaffService.add(verificationByStaff);
        return new SuccessResult();
    }
    
}

package com.CCT.HRMS.core.Verification;

import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.concretes.Users.User;

public interface VerificationUtilService {
    
    public Result verificationByEmail(User user);
    public Result verificationByStaff(User user);
}

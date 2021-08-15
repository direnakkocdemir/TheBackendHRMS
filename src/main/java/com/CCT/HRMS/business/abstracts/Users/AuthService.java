package com.CCT.HRMS.business.abstracts.Users;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.EmployerRegisterDto;
import com.CCT.HRMS.entities.DTOs.JobseekerRegisterDto;
import com.CCT.HRMS.entities.DTOs.LoginDto;
import com.CCT.HRMS.entities.DTOs.UserLoginReturnDto;

/**
 * This interface class is for business layer of Auth
 * 
 * @author diren
 *
 */


public interface AuthService {
    
    public Result employerRegistration(EmployerRegisterDto employerRegisterDto);

    public Result jobseekerRegistration(JobseekerRegisterDto jobseekerRegisterDto);

    public DataResult<UserLoginReturnDto> login(LoginDto loginDto);
}

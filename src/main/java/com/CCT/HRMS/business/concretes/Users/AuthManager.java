package com.CCT.HRMS.business.concretes.Users;

import com.CCT.HRMS.business.abstracts.ResumeService;
import com.CCT.HRMS.business.abstracts.Users.AuthService;
import com.CCT.HRMS.business.abstracts.Users.EmployerService;
import com.CCT.HRMS.business.abstracts.Users.JobseekerService;
import com.CCT.HRMS.business.abstracts.Users.StaffService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.core.Utilities.BusinessRules;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.core.Verification.VerificationUtilService;
import com.CCT.HRMS.dataAccess.abstracts.Users.EmployerDao;
import com.CCT.HRMS.dataAccess.abstracts.Users.JobseekerDao;
import com.CCT.HRMS.dataAccess.abstracts.Users.StaffDao;
import com.CCT.HRMS.entities.DTOs.EmployerRegisterDto;
import com.CCT.HRMS.entities.DTOs.JobseekerRegisterDto;
import com.CCT.HRMS.entities.DTOs.LoginDto;
import com.CCT.HRMS.entities.DTOs.UserLoginReturnDto;
import com.CCT.HRMS.entities.concretes.Users.Employer;
import com.CCT.HRMS.entities.concretes.Users.Jobseeker;
import com.CCT.HRMS.entities.concretes.Users.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class AuthManager implements AuthService {

    // Properties
    private UserService userService;
    private EmployerService employerService;
    private JobseekerService jobseekerService;
    private StaffService staffService;
    private EmployerDao employerDao;
    private JobseekerDao jobseekerDao;
    private StaffDao staffDao;
    private VerificationUtilService verificationUtilService;
    private ResumeService resumeService;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public AuthManager(UserService userService, EmployerService employerService, JobseekerService jobseekerService,
            StaffService staffService, EmployerDao employerDao, JobseekerDao jobseekerDao, StaffDao staffDao,
            VerificationUtilService verificationUtilService,ResumeService resumeService) {
        this.userService = userService;
        this.employerService = employerService;
        this.jobseekerService = jobseekerService;
        this.staffService = staffService;
        this.employerDao = employerDao;
        this.jobseekerDao = jobseekerDao;
        this.staffDao = staffDao;
        this.verificationUtilService = verificationUtilService;
        this.resumeService = resumeService;
    }

    @Override
    public Result employerRegistration(EmployerRegisterDto employerRegisterDto) {
        Employer employer = employerHelper(employerRegisterDto);
        Result result = BusinessRules.run(checkNullInformationForEmployer(employer),
                checkPasswordMatches(employer.getPassword(), employerRegisterDto.getConfirmPassword()),
                checkIfEmailExists(employer.getEmail()),
                checkDomainMatches(employer.getEmail(), employer.getWebsite()));
        if (result.isSuccess()) {
            employerService.add(employer);
            verificationUtilService.verificationByEmail(employer);
            verificationUtilService.verificationByStaff(employer);
            return new SuccessResult(Messages.EmployerAdded);
        }
        return new ErrorResult(result.getMessage());
    }

    @Override
    public Result jobseekerRegistration(JobseekerRegisterDto jobseekerRegisterDto) {
        Jobseeker jobseeker = jobseekerHelper(jobseekerRegisterDto);
        Result result = BusinessRules.run(checkNullInformationForJobseeker(jobseeker),
                checkIfEmailExists(jobseekerRegisterDto.getEmail()),
                checkPasswordMatches(jobseeker.getPassword(), jobseekerRegisterDto.getConfirmPassword()));
        if (result.isSuccess()) {
            System.out.println("Success");
            jobseekerService.add(jobseeker);
            resumeService.add(jobseekerDao.getUserByEmail(jobseeker.getEmail()).getId());
            verificationUtilService.verificationByEmail(jobseeker);
            return new SuccessResult(Messages.JobseekerAdded);
        }
        return new ErrorResult(result.getMessage());
    }

    @Override
    public DataResult<UserLoginReturnDto> login(LoginDto loginDto) {
        Result result = BusinessRules.run(checkUserIsRegistered(loginDto.getEmail()), checkPassword(loginDto));
        if (result.isSuccess()) {

            User user = userService.getUserByEmail(loginDto.getEmail()).getData();
            UserLoginReturnDto userLoginReturnDto = new UserLoginReturnDto();
            userLoginReturnDto.setId(user.getId());
            userLoginReturnDto.setEmail(user.getEmail());

            if (this.jobseekerDao.existsById(user.getId())) {
                userLoginReturnDto.setUserType(1);
                userLoginReturnDto.setName(this.jobseekerService.getJobseekerById(user.getId()).getData().getFirstName()
                        + " " + this.jobseekerService.getJobseekerById(user.getId()).getData().getLastName());
            } else if (this.employerDao.existsById(user.getId())) {
                userLoginReturnDto.setUserType(2);
                userLoginReturnDto
                        .setName(this.employerService.getEmployerById(user.getId()).getData().getCompanyName());
            } else if (this.staffDao.existsById(user.getId())) {
                userLoginReturnDto.setUserType(3);
                userLoginReturnDto.setName(this.staffService.getStaffById(user.getId()).getData().getFirstName() + " "
                        + this.staffService.getStaffById(user.getId()).getData().getLastName());
            }
            String token = JWTIssuer.createJWT(loginDto.getEmail(), "hrms-application", loginDto.getEmail(), 8640000);
            userLoginReturnDto.setToken(token);
            System.out.println("Successfully logged in");
            return new SuccessDataResult<>(userLoginReturnDto);
        }
        return new ErrorDataResult<>(result.getMessage());
    }

    // Validation rules //
    private Result checkNullInformationForEmployer(Employer employer) {
        if (employer.getCompanyName() != null && employer.getWebsite() != null && employer.getEmail() != null
                && employer.getPhone() != null && employer.getPassword() != null) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.NullInformation);
    }

    private Result checkNullInformationForJobseeker(Jobseeker jobseeker) {
        if (jobseeker.getFirstName() != null && jobseeker.getEmail() != null && jobseeker.getPassword() != null
                && jobseeker.getDateOfBirth() != null && jobseeker.getLastName() != null) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.NullInformation);
    }

    private Result checkPasswordMatches(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.PasswordsAreNotMatched);
    }

    private Result checkDomainMatches(String email, String website) {
        String domain = email.substring(email.indexOf("@") + 1);
        System.out.println(domain);
        if (website.contains(domain)) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.DomainsAreNotMatching);
    }

    private Result checkIfEmailExists(String email) {

        if (!this.userService.getUserByEmail(email).isSuccess()) {

            return new SuccessResult();
        }

        return new ErrorResult(Messages.EmailIsExisted);
    }

    private Result checkUserIsRegistered(String email) {
        if (!this.userService.getUserByEmail(email).isSuccess()) {
            return new ErrorResult(Messages.UserNotFound);
        }
        return new SuccessResult();
    }

    private Result checkPassword(LoginDto loginDto) {
        if (this.userService.getUserByEmail(loginDto.getEmail()).getData().getPassword()
                .equals(loginDto.getPassword())) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.PasswordsAreNotMatched);
    }

    // Helper
    private Employer employerHelper(EmployerRegisterDto employerRegisterDto) {
        Employer employer = new Employer();
        employer.setCompanyName(employerRegisterDto.getCompanyName());
        employer.setEmail(employerRegisterDto.getEmail());
        employer.setPassword(employerRegisterDto.getPassword());
        employer.setPhone(employerRegisterDto.getPhone());
        employer.setWebsite(employerRegisterDto.getWebsite());
        employer.setPublishedYear(employerRegisterDto.getPublishedYear());
        employer.setIndustry(employerRegisterDto.getIndustry());
        return employer;
    }

    private Jobseeker jobseekerHelper(JobseekerRegisterDto jobseekerRegisterDto) {
        Jobseeker jobseeker = new Jobseeker();
        jobseeker.setDateOfBirth(jobseekerRegisterDto.getDateOfBirth());
        jobseeker.setEmail(jobseekerRegisterDto.getEmail());
        jobseeker.setFirstName(jobseekerRegisterDto.getFirstName());
        jobseeker.setLastName(jobseekerRegisterDto.getLastName());
        jobseeker.setPassword(jobseekerRegisterDto.getPassword());
        jobseeker.setJobTitle(jobseekerRegisterDto.getJobTitle());
        return jobseeker;
    }

}

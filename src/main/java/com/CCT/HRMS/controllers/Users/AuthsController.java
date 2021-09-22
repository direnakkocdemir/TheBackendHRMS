package com.CCT.HRMS.controllers.Users;

import com.CCT.HRMS.business.abstracts.Users.AuthService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.EmployerRegisterDto;
import com.CCT.HRMS.entities.DTOs.JobseekerRegisterDto;
import com.CCT.HRMS.entities.DTOs.LoginDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthsController {

    // Properties
    private AuthService authService;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public AuthsController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registration for employer
     * @param employerRegisterDto
     * @return
     */
    @PostMapping("/employer")
    public ResponseEntity<?> registerEmployer(@RequestBody EmployerRegisterDto employerRegisterDto) {
        Result result = authService.employerRegistration(employerRegisterDto);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
    /**
     * Registration for jobseeker
     * @param jobseekerRegisterDto
     * @return
     */
    @PostMapping("/jobseeker")
    public ResponseEntity<?> registerJobseeker(@RequestBody JobseekerRegisterDto jobseekerRegisterDto) {
       Result result = authService.jobseekerRegistration(jobseekerRegisterDto);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    /**
     * Login function for all of the users
     * @param loginDto
     * @return
     */
    @PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		Result result = authService.login(loginDto);
		// If result is successful, return the token with Ok status
		if (result.isSuccess()) {
			// Creating token
			// String token = JWTIssuer.createJWT(loginDto.getEmail(), "hrms-application", loginDto.getEmail(), 8640000);
			return ResponseEntity.ok(result);
		}
		// If result is not successful, return message with bad request status
		return ResponseEntity.badRequest().body(result);
	}
}

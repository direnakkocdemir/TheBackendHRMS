package com.CCT.HRMS.controllers;

import com.CCT.HRMS.business.abstracts.ApplicationService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;

@RestController
@CrossOrigin
@RequestMapping("api/applications/")
public class ApplicationsController {

	// Properties
	private ApplicationService applicationService;
	private UserService userService;

	// Constructor
	@Autowired // Spring bean annotation injects object dependency implicitly
	public ApplicationsController(ApplicationService applicationService, UserService userService) {
		this.applicationService = applicationService;
		this.userService = userService;
	}

	// /**
	// * Getting all applications
	// * @return
	// */
	// @GetMapping("getall")
	// public ResponseEntity<List<Application>> getAll() {
	// var result = applicationService.getAll();
	// if (result.isSuccess()) {
	// return new ResponseEntity<List<Application>>(result.getData(),
	// HttpStatus.OK);
	// }
	// return new ResponseEntity<List<Application>>(HttpStatus.BAD_REQUEST);
	// }

	/**
	 * Getting the applications by advertisement id
	 * 
	 * @param token
	 * @param advertisementId
	 * @return
	 */
	@GetMapping("getbyadid")
	public ResponseEntity<?> getByAdvertisementId(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam(name = "id") int advertisementId) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = applicationService.getAllByAdvertisementId(advertisementId);
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Getting applications by jobseeker id
	 * 
	 * @param token
	 * @param id
	 * @return
	 */
	@GetMapping("getbyjobseekerid")
	public ResponseEntity<?> getByJobseekerId(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam(name = "id") int id) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = applicationService.getAllByJobseekerId(id);
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Checking token is valid or not
	 * 
	 * @param token Token is key to access to system
	 * @return if it is valid true, otherwise false
	 */
	private boolean checkingToken(String token) {
		Claims claims = JWTIssuer.decodeJWT(token.split(" ")[1]);
		String subClaim = claims.get("sub", String.class);
		if (userService.checkUser(subClaim).isSuccess()) {
			return true;
		}
		return false;
	}
}

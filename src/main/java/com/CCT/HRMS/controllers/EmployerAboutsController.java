package com.CCT.HRMS.controllers;

import com.CCT.HRMS.business.abstracts.EmployerAboutService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.DTOs.EmployerAboutDto;
import com.CCT.HRMS.entities.DTOs.IdDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;

@RestController
@CrossOrigin
@RequestMapping("api/employerAbouts/")
public class EmployerAboutsController {

	// Properties
	private EmployerAboutService employerAboutService;
	private UserService userService;

	// Constructor
	@Autowired // Spring bean annotation injects object dependency implicitly
	public EmployerAboutsController(EmployerAboutService employerAboutService, UserService userService) {
		this.employerAboutService = employerAboutService;
		this.userService = userService;
	}

	/**
	 * Adding the about for employer to the data base
	 * 
	 * @param token
	 * @param employerAboutDto
	 * @return
	 */
	@PostMapping("add")
	public ResponseEntity<?> add(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestBody EmployerAboutDto employerAboutDto) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = employerAboutService.add(employerAboutDto);
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	// /**
	// * Getting all employers abouts
	// * @return
	// */
	// @GetMapping("getall")
	// public ResponseEntity<List<EmployerAbout>> getAll(){
	// var result = employerAboutService.getAll();
	// if(result.isSuccess()){
	// return new
	// ResponseEntity<List<EmployerAbout>>(result.getData(),HttpStatus.OK);
	// }
	// return new ResponseEntity<List<EmployerAbout>>(HttpStatus.BAD_REQUEST);
	// }

	/**
	 * Deleting the employer's about from the system by employer id
	 * 
	 * @param token
	 * @param idDto
	 * @return
	 */
	@PostMapping("delete")
	public ResponseEntity<?> delete(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestBody IdDto idDto) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = employerAboutService.delete(idDto.getId());
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Getting the employer about by employer's id
	 * 
	 * @param token
	 * @param employerId
	 * @return
	 */
	@GetMapping("get")
	public ResponseEntity<?> get(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam(name = "id") int employerId) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = employerAboutService.getEmployerAboutByEmployerId(employerId);
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

package com.CCT.HRMS.controllers;

import java.util.List;

import com.CCT.HRMS.business.abstracts.ResumeService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.DTOs.ResumeDto;
import com.CCT.HRMS.entities.concretes.Resume;

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
@RequestMapping("/api/resumes/")
public class ResumesController {

	// Properties
	private ResumeService resumeService;
	private UserService userService;

	// Constructor
	@Autowired // Spring bean annotation injects object dependency implicitly
	public ResumesController(ResumeService resumeService, UserService userService) {
		this.resumeService = resumeService;
		this.userService = userService;
	}

	// /**
	// * add the resume by jobseeker id
	// * @param jobseekerId
	// * @return
	// */
	// @PostMapping("add")
	// public ResponseEntity<?> add(@RequestHeader(name = "Authorization", required
	// = true) String token,
	// @RequestParam int jobseekerId) {
	// // Checking token is valid or not
	// if (checkingToken(token)) {
	// Result result = resumeService.add(jobseekerId);
	// if (result.isSuccess()) {
	// return ResponseEntity.ok(result);
	// }
	// return ResponseEntity.badRequest().body(result);
	// }
	// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	// }

	/**
	 * Updating the resume in the database
	 * 
	 * @param token
	 * @param resume
	 * @return
	 */
	@PostMapping("update")
	public ResponseEntity<?> update(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestBody Resume resume) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = resumeService.update(resume);
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	// /**
	// * Getting all resumes
	// * @return
	// */
	// @GetMapping("getall")
	// public ResponseEntity<List<Resume>> getAll() {
	// var result = resumeService.getAll();
	// if (result.isSuccess()) {
	// return new ResponseEntity<List<Resume>>(result.getData(), HttpStatus.OK);
	// }
	// return new ResponseEntity<List<Resume>>(HttpStatus.BAD_REQUEST);
	// }

	/**
	 * Getting resume by jobseeker id
	 * 
	 * @param token
	 * @param id
	 * @return
	 */
	@GetMapping("get")
	public ResponseEntity<?> getByJobseekerId(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam(name = "id") int id) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = resumeService.getResumeByJobseekerId(id);
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Getting resume id by jobseeker id
	 * 
	 * @param token
	 * @param id
	 * @return
	 */
	@GetMapping("getid")
	public ResponseEntity<?> getIdByJobseekerId(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam(name = "id") int id) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = resumeService.getIdByJobseekerId(id);
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

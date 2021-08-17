package com.CCT.HRMS.controllers;

import com.CCT.HRMS.business.abstracts.ImageService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.DTOs.ImageForAddDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.Claims;

@RestController
@CrossOrigin
@RequestMapping("/api/images/")
public class ImagesController {

	// Properties
	private ImageService imageService;
	private UserService userService;

	// Constructor
	@Autowired // Spring bean annotation injects object dependency implicitly
	public ImagesController(com.CCT.HRMS.business.abstracts.ImageService imageService, UserService userService) {
		this.imageService = imageService;
		this.userService = userService;

	}

	/**
	 * Uploading a image to the data base by type, user id and image file
	 * 
	 * @param token
	 * @param imageType
	 * @param jobseekerId
	 * @param multipartFile
	 * @return
	 */
	@PostMapping("upload")
	public ResponseEntity<?> upload(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam("imageType") int imageType, @RequestParam("jobseekerId") int jobseekerId,
			@RequestParam("multipartFile") MultipartFile multipartFile) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			ImageForAddDto image = new ImageForAddDto(jobseekerId, imageType);
			Result result = imageService.add(image, multipartFile);
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

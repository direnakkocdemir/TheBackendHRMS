package com.CCT.HRMS.controllers.ResumeInfos;

import com.CCT.HRMS.business.abstracts.ResumeInfos.AboutService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.DTOs.AboutForAddDto;
import com.CCT.HRMS.entities.DTOs.IdDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.About;

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
@RequestMapping("api/abouts/")
public class AboutsController {

	// Properties
	private AboutService aboutService;
	private UserService userService;

	// Constructor
	@Autowired // Spring bean annotation injects object dependency implicitly
	public AboutsController(AboutService aboutService, UserService userService) {
		this.aboutService = aboutService;
		this.userService = userService;
	}

	/**
	 * Adding an about to the system
	 * 
	 * @param token
	 * @param about
	 * @return
	 */
	@PostMapping("add")
	public ResponseEntity<?> add(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestBody AboutForAddDto about) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = aboutService.add(about);
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Deleting the about by defined about id
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
			Result result = aboutService.delete(idDto.getId());
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Updating the about
	 * 
	 * @param token
	 * @param about
	 * @return
	 */
	@PostMapping("update")
	public ResponseEntity<?> update(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestBody About about) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = aboutService.update(about);
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//    /**
//     * Getting all the jobseekers' abouts
//     * @return
//     */
//    @GetMapping("getall")
//    public ResponseEntity<List<About>> getAll() {
//        var result = aboutService.getAll();
//        if (result.isSuccess()) {
//            return new ResponseEntity<List<About>>(result.getData(), HttpStatus.OK);
//        }
//        return new ResponseEntity<List<About>>(HttpStatus.BAD_REQUEST);
//    }

	/**
	 * Getting the about by resume id
	 * 
	 * @param token
	 * @param id
	 * @return
	 */
	@GetMapping("getbyresumeid")
	public ResponseEntity<?> get(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam(name = "id") int id) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = aboutService.getAboutByResumeId(id);
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

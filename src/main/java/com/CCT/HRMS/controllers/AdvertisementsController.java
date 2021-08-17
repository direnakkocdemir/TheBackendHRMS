package com.CCT.HRMS.controllers;

import java.util.List;

import com.CCT.HRMS.business.abstracts.AdvertisementService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.DTOs.AdvertisementDto;
import com.CCT.HRMS.entities.DTOs.ApplyDto;
import com.CCT.HRMS.entities.DTOs.IdDto;
import com.CCT.HRMS.entities.concretes.Advertisement;

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
@RequestMapping("/api/advertisements/")
public class AdvertisementsController {

	// Properties
	private AdvertisementService advertisementService;
	private UserService userService;

	// Constructor
	@Autowired // Spring bean annotation injects object dependency implicitly
	public AdvertisementsController(AdvertisementService advertisementService, UserService userService) {
		this.advertisementService = advertisementService;
		this.userService = userService;
	}

	/**
	 * Getting advertisements by advertisement id
	 * 
	 * @param token
	 * @param id
	 * @return
	 */
	@GetMapping("get")
	public ResponseEntity<?> get(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam(name = "id") int id) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = advertisementService.getJobAdById(id);
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//    /**
//     * Gettig all of the advertisements
//     * @return
//     */
//    @GetMapping("getall")
//    public ResponseEntity<Result> getAll() {
//        Result result = advertisementService.getAll();
//        if (result.isSuccess()) {
//            return ResponseEntity.ok(result);
//        }
//        return ResponseEntity.badRequest().body(result);
//    }

	/**
	 * Getting all the advertisements in defined pages and defined amount
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("getallpages")
	public ResponseEntity<?> getAllByPage(@RequestParam(name = "pageNo") int pageNo,
			@RequestParam(name = "pageSize") int pageSize) {
		// Checking token is valid or not
		Result result = advertisementService.getAll(pageNo, pageSize);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	/**
	 * Adding a job advertisement to the system
	 * 
	 * @param token
	 * @param advertisement
	 * @return
	 */
	@PostMapping("add")
	public ResponseEntity<?> add(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestBody AdvertisementDto advertisement) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = advertisementService.add(advertisement);
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Getting the job advertisement by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("getByJobAdId")
	public ResponseEntity<?> getByJobAdId(@RequestBody int id) {
		Result result = advertisementService.getJobAdById(id);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	/**
	 * Getting employer's advertisements by employer name from defined pages and
	 * defined amount
	 * 
	 * @param token
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("getByEmployerName")
	public ResponseEntity<?> getJobAdByEmployerName(
			@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam(name = "name") String name, @RequestParam(name = "pageNo") int pageNo,
			@RequestParam(name = "pageSize") int pageSize) {

		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = advertisementService.getJobAdsByEmployerName(name, pageNo, pageSize);
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Getting advertisements by jobtitle from defined pages and defined amount
	 * 
	 * @param jobTitle
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("getallbyjobtitle")
	public ResponseEntity<?> getAllByJobTitle(@RequestParam(name = "jobTitle") String jobTitle,
			@RequestParam(name = "pageNo") int pageNo, @RequestParam(name = "pageSize") int pageSize) {
		Result result = advertisementService.getJobAdsByJobTitle(jobTitle, pageNo, pageSize);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	/**
	 * Getting advertisements by jobtitle and location from defined pages and
	 * defined amount
	 * 
	 * @param jobTitle
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("getallbyjobtitleandlocation")
	public ResponseEntity<?> getAllByJobTitleAndLocation(@RequestParam(name = "jobTitle") String jobTitle,
			@RequestParam(name = "location") int location, @RequestParam(name = "pageNo") int pageNo,
			@RequestParam(name = "pageSize") int pageSize) {
		Result result = advertisementService.getJobAdsByJobTitleAndLocation(jobTitle, location, pageNo, pageSize);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	/**
	 * Getting advertisements by location from defined pages and defined amount
	 * 
	 * @param jobTitle
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("getallbylocation")
	public ResponseEntity<?> getAllByLocation(@RequestParam(name = "location") int location,
			@RequestParam(name = "pageNo") int pageNo, @RequestParam(name = "pageSize") int pageSize) {
		Result result = advertisementService.getJobAdsByLocation(location, pageNo, pageSize);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	/**
	 * Adding a application to the system ( application)
	 * 
	 * @param jobAdId
	 * @param jobseekerId
	 * @return
	 */
	@PostMapping("apply")
	public ResponseEntity<?> apply(@RequestHeader(name = "Authorization", required = false) String token,
			@RequestBody ApplyDto applyDto) {

		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = advertisementService.application(applyDto.getAdvertisementId(), applyDto.getJobseekerId());
			if (result.isSuccess()) {
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.badRequest().body(result);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Getting advertisements by jobtitle, work time and location from defined pages
	 * and defined amount
	 * 
	 * @param jobTitle
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/getbyall")
	public ResponseEntity<?> getByAll(@RequestParam(name = "jobTitle") String jobTitle,
			@RequestParam(name = "location") int location, @RequestParam(name = "workTime") int workTime,
			@RequestParam(name = "pageNo") int pageNo, @RequestParam(name = "pageSize") int pageSize) {
		Result result = advertisementService.getJobAdsByJobTitleAndLocationAndWorkTime(jobTitle, location, workTime,
				pageNo, pageSize);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	/**
	 * Getting advertisements by location and work time from defined pages and
	 * defined amount
	 * 
	 * @param location
	 * @param workTime
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("getallbylocationandworktime")
	public ResponseEntity<?> getAllByLocationAndWorkTime(@RequestParam(name = "location") int location,
			@RequestParam(name = "workTime") int workTime, @RequestParam(name = "pageNo") int pageNo,
			@RequestParam(name = "pageSize") int pageSize) {
		Result result = advertisementService.getJobAdsByLocationAndWorkTime(location, workTime, pageNo, pageSize);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	/**
	 * Getting advertisements by work time from defined pages and defined amount
	 * 
	 * @param workTime
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("getallbyworktime")
	public ResponseEntity<?> getAllByWorkTime(@RequestParam(name = "workTime") int workTime,
			@RequestParam(name = "pageNo") int pageNo, @RequestParam(name = "pageSize") int pageSize) {
		Result result = advertisementService.getJobAdsByWorkTime(workTime, pageNo, pageSize);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	/**
	 * Getting advertisements by job title and work time from defined pages and
	 * defined amount
	 * 
	 * @param jobTitle
	 * @param workTime
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("getallbyjobtitleandworktime")
	public ResponseEntity<?> getAllByJobTitleAndWorkTime(@RequestParam(name = "jobTitle") String jobTitle,
			@RequestParam(name = "workTime") int workTime, @RequestParam(name = "pageNo") int pageNo,
			@RequestParam(name = "pageSize") int pageSize) {
		Result result = advertisementService.getJobAdsByJobTitleAndWorkTime(jobTitle, workTime, pageNo, pageSize);
		if (result.isSuccess()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(result);
	}

	/**
	 * Closing the advertisement
	 * 
	 * @param token
	 * @param idDto
	 * @return
	 */
	@PostMapping("close")
	public ResponseEntity<?> close(@RequestHeader(name = "Authorization", required = false) String token,
			@RequestBody IdDto idDto) {
		System.out.print(token);
		// Checking token is valid or not
		if (checkingToken(token)) {
			Result result = advertisementService.closeAdvertisement(idDto.getId());
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

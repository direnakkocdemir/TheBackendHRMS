package com.CCT.HRMS.controllers;

import java.util.List;

import com.CCT.HRMS.business.abstracts.AdvertisementService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.DTOs.AdvertisementDto;
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
     * 
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

    /**
     * 
     * @return
     */
    @GetMapping("getall")
    public ResponseEntity<Result> getAll() {
        Result result = advertisementService.getAll();
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    /**
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
     * 
     * @param jobAd
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
     * 
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
     * 
     * @param jobTitle
     * @param location
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
     * 
     * @param location
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
     * 
     * @param jobAdId
     * @param jobseekerId
     * @return
     */
    @PostMapping("apply")
    public ResponseEntity<?> apply(@RequestHeader(name = "Authorization", required = false) String token,
            @RequestParam(name = "advertisementId") int jobAdId,
            @RequestParam(name = "jobseekerId") int jobseekerId) {

        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = advertisementService.application(jobAdId, jobseekerId);
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().body(result);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * 
     * @param jobTitle
     * @param location
     * @param workTime
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
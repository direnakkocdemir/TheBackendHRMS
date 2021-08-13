package com.CCT.HRMS.controllers;

import java.util.List;

import com.CCT.HRMS.business.abstracts.WorkTimeService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.DTOs.WorkTimeDto;
import com.CCT.HRMS.entities.concretes.WorkTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/workTimes/")
@CrossOrigin
public class WorkTimesController {

    // Properties
    private WorkTimeService workTimeService;
    private UserService userService;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public WorkTimesController(WorkTimeService workTimeService, UserService userService) {
        this.workTimeService = workTimeService;
        this.userService = userService;
    }

    /**
     * 
     * @return
     */
    @GetMapping("getall")
    public ResponseEntity<List<WorkTime>> getAll() {
        var result = workTimeService.getAll();
        if (result.isSuccess()) {
            return new ResponseEntity<List<WorkTime>>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 
     * @return
     */
    @GetMapping("getallfordropdown")
    public ResponseEntity<List<WorkTimeDto>> getAllForDropdown() {
        var result = workTimeService.getAllForDropdown();
        if (result.isSuccess()) {
            return new ResponseEntity<List<WorkTimeDto>>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 
     * @param workTime
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<?> add(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody WorkTime workTime) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = workTimeService.add(workTime);
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

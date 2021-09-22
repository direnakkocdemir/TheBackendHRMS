package com.CCT.HRMS.controllers.Users;

import java.util.List;

import com.CCT.HRMS.business.abstracts.Users.JobseekerService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.concretes.Users.Jobseeker;

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
@RequestMapping("/api/jobseekers/")
public class JobseekersController {

    // Properties
    private JobseekerService jobseekerService;
    private UserService userService;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public JobseekersController(JobseekerService jobseekerService, UserService userService) {
        this.jobseekerService = jobseekerService;
        this.userService = userService;
    }

    /**
     * Adding a jobseeker to the system
     * @param jobseeker
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<String> add(@RequestBody Jobseeker jobseeker) {
        var result = jobseekerService.add(jobseeker);
        if (result.isSuccess()) {
            return new ResponseEntity<String>(result.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Deleting a jobseeker from database
     * @param jobseeker
     * @return
     */
    @PostMapping("delete")
    public ResponseEntity<String> delete(@RequestBody Jobseeker jobseeker) {
        var result = jobseekerService.delete(jobseeker);
        if (result.isSuccess()) {
            return new ResponseEntity<String>(result.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Updating the jobseeker
     * @param jobseeker
     * @return
     */
    @PostMapping("update")
    public ResponseEntity<String> update(@RequestBody Jobseeker jobseeker) {
        var result = jobseekerService.update(jobseeker);
        if (result.isSuccess()) {
            return new ResponseEntity<String>(result.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Getting a jobseeker by jobseeker id 
     * @param id
     * @return
     */
    @GetMapping("get")
    public ResponseEntity<Jobseeker> getById(@RequestParam(name = "id") int id) {
        var result = jobseekerService.getJobseekerById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Getting all of the jobseekers
     * @return
     */
    @GetMapping("getall")
    public ResponseEntity<List<Jobseeker>> getAll() {
        var result = jobseekerService.getAll();
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Getting jobseeker's detail by jobseeker id 
     * @param token
     * @param id
     * @return
     */
    @GetMapping("getDetails")
    public ResponseEntity<?> getByJobseekerId(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestParam(name = "id") int id) {

        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = jobseekerService.getJobseekerInfoById(id);
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

package com.CCT.HRMS.controllers.ResumeInfos;

import java.util.List;

import com.CCT.HRMS.business.abstracts.ResumeInfos.EducationService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.DTOs.EducationForAddDto;
import com.CCT.HRMS.entities.DTOs.IdDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Education;

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
@RequestMapping("/api/educations/")
public class EducationsController {

    // Properties
    private EducationService educationService;
    private UserService userService;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public EducationsController(EducationService educationService, UserService userService) {
        this.educationService = educationService;
        this.userService= userService;
    }

    /**
     * 
     * @param education
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<?> add(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody EducationForAddDto education) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = educationService.add(education);
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
    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody IdDto idDto) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = educationService.delete(idDto.getId());
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().body(result);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * 
     * @param education
     * @return
     */
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody Education education) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = educationService.update(education);
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
    public ResponseEntity<List<Education>> getAll() {
        var result = educationService.getAll();
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        Result result = educationService.getEducationByResumeId(id);
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

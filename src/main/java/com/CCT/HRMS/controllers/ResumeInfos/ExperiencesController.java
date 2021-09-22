package com.CCT.HRMS.controllers.ResumeInfos;

import com.CCT.HRMS.business.abstracts.ResumeInfos.ExperienceService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.DTOs.ExperienceForAddDto;
import com.CCT.HRMS.entities.DTOs.IdDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Experience;

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
@RequestMapping("/api/experiences/")
public class ExperiencesController {

    // Properties
    private ExperienceService experienceService;
    private UserService userService;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public ExperiencesController(ExperienceService experienceService, UserService userService) {
        this.experienceService = experienceService;
        this.userService = userService;
    }

    /**
     * Adding an experience to the database
     * @param token
     * @param experience
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<?> add(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody ExperienceForAddDto experience) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = experienceService.add(experience);
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().body(result);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Deleting an experience from the system by defined education id
     * @param token
     * @param idDto
     * @return
     */
    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestHeader(name = "Authorization", required = true) String token,
    		@RequestBody IdDto idDto) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            var result = experienceService.delete(idDto.getId());
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().body(result);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Updating the experience 
     * @param token
     * @param experience
     * @return
     */
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody Experience experience) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = experienceService.update(experience);
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().body(result);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

//    /**
//     * Getting all of the experiences
//     * @return
//     */
//    @GetMapping("getall")
//    public ResponseEntity<List<Experience>> getAll() {
//        var result = experienceService.getAll();
//        if (result.isSuccess()) {
//            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

    /**
     * Getting an experience from database by defined id
     * @param token
     * @param id
     * @return
     */
    @GetMapping("get")
    public ResponseEntity<?> get(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestParam(name = "id") int id) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = experienceService.getExperienceByResumeId(id);
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

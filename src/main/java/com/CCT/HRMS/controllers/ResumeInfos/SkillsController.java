package com.CCT.HRMS.controllers.ResumeInfos;

import java.util.List;

import com.CCT.HRMS.business.abstracts.ResumeInfos.SkillService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.DTOs.SkillForAddDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Skill;

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
@RequestMapping("/api/skills/")
public class SkillsController {

    // Properties
    private SkillService skillService;
    private UserService userService;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public SkillsController(SkillService skillService, UserService userService) {
        this.skillService = skillService;
        this.userService = userService;
    }

    /**
     * 
     * @param skill
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<?> add(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody SkillForAddDto skill) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = skillService.add(skill);
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
            @RequestBody int id) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = skillService.delete(id);
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().body(result);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * 
     * @param skill
     * @return
     */
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody Skill skill) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = skillService.update(skill);
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
    public ResponseEntity<List<Skill>> getAll() {
        var result = skillService.getAll();
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
    public ResponseEntity<?> getAll(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestParam(name = "id") int id) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = skillService.getSkillByResumeId(id);
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
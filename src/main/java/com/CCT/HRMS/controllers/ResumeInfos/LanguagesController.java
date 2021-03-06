package com.CCT.HRMS.controllers.ResumeInfos;

import com.CCT.HRMS.business.abstracts.ResumeInfos.LanguageService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.DTOs.IdDto;
import com.CCT.HRMS.entities.DTOs.LanguageForAddDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Language;

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
@RequestMapping("/api/languages/")
public class LanguagesController {

    // Properties
    private LanguageService languageService;
    private UserService userService;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public LanguagesController(LanguageService languageService, UserService userService) {
        this.languageService = languageService;
        this.userService = userService;
    }

    /**
     * Adding a language to the database
     * @param token
     * @param language
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<?> add(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody LanguageForAddDto language) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = languageService.add(language);
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().body(result);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Deleting a language from database by defined id
     * @param token
     * @param idDto
     * @return
     */
    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestHeader(name = "Authorization", required = true) String token,
    		@RequestBody IdDto idDto) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = languageService.delete(idDto.getId());
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().body(result);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Updating the language
     * @param token
     * @param language
     * @return
     */
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody Language language) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = languageService.update(language);
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().body(result);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

//    /**
//     * Getting all of the languages
//     * @return
//     */
//    @GetMapping("getall")
//    public ResponseEntity<List<Language>> getAll() {
//        var result = languageService.getAll();
//        if (result.isSuccess()) {
//            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

    /**
     * Getting a language from database by defined id
     * @param token
     * @param id
     * @return
     */
    @GetMapping("get")
    public ResponseEntity<?> get(@RequestHeader(name = "Authorization", required = true) String token,
            @RequestParam(name = "id") int id) {
        // Checking token is valid or not
        if (checkingToken(token)) {
            Result result = languageService.getLanguageByResumeId(id);
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

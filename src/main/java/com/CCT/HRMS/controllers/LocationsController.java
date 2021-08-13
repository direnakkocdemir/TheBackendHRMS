package com.CCT.HRMS.controllers;

import java.util.List;

import com.CCT.HRMS.business.abstracts.LocationService;
import com.CCT.HRMS.business.abstracts.Users.UserService;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Utilities.Token.JWTIssuer;
import com.CCT.HRMS.entities.DTOs.LocationDto;
import com.CCT.HRMS.entities.concretes.Location;

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
@CrossOrigin
@RequestMapping("/api/location")
public class LocationsController {
    
    // Properties
    private LocationService locationService;
    private UserService userService;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public LocationsController(LocationService locationService, UserService userService) {
        this.locationService = locationService;
        this.userService = userService;
    }

    /**
     * 
     * @return
     */
    @GetMapping("getall")
    public ResponseEntity<List<Location>> getAll(){
        var result = locationService.getAll();
        if(result.isSuccess()){
            return new ResponseEntity<List<Location>>(result.getData(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 
     * @return
     */
    @GetMapping("getallfordropdown")
    public ResponseEntity<List<LocationDto>> getAllForDropdown(){
        var result = locationService.getAllForDropdown();
        if(result.isSuccess()){
            return new ResponseEntity<List<LocationDto>>(result.getData(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 
     * @param location
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<?> add(@RequestHeader(name = "Authorization", required = true) String token,
    @RequestBody Location location){
        // Checking token is valid or not
        if (checkingToken(token)) {
        Result result = locationService.add(location);
        if(result.isSuccess()){
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

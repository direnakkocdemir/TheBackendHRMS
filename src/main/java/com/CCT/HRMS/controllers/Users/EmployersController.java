package com.CCT.HRMS.controllers.Users;

import java.util.List;

import com.CCT.HRMS.business.abstracts.Users.EmployerService;
import com.CCT.HRMS.entities.DTOs.EmployerInfoDto;
import com.CCT.HRMS.entities.concretes.Users.Employer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * EmployersController
 */
@RestController
@CrossOrigin
@RequestMapping("/api/employers/")
public class EmployersController {

    // Properties
    private EmployerService employerService;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public EmployersController(EmployerService employerService) {
        this.employerService = employerService;
    }

    /**
     * Adding an employer to database
     * @param employer
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<String> add(@RequestBody Employer employer){
        var result = employerService.add(employer);
        if(result.isSuccess()){
            return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
        }
        return new ResponseEntity<>(result.getMessage(),HttpStatus.BAD_REQUEST);
    }

    /**
     * Deleting an employer from database
     * @param employer
     * @return
     */
    @PostMapping("delete")
    public ResponseEntity<String> delete(@RequestBody Employer employer){
        var result = employerService.delete(employer);
        if(result.isSuccess()){
            return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
        }
        return new ResponseEntity<>(result.getMessage(),HttpStatus.BAD_REQUEST);
    }

    /**
     * Updating the employer
     * @param employer
     * @return
     */
    @PostMapping("update")
    public ResponseEntity<String> update(@RequestBody Employer employer){
        var result = employerService.update(employer);
        if(result.isSuccess()){
            return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
        }
        return new ResponseEntity<>(result.getMessage(),HttpStatus.BAD_REQUEST);
    }

    /**
     * Getting an employer by id
     * @param id
     * @return
     */
    @GetMapping("get")
    public ResponseEntity<Employer> getById(@RequestParam int id){
        var result = employerService.getEmployerById(id);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getData(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    /**
     * Getting all of the employers
     * @return
     */
    @GetMapping("getall")
    public ResponseEntity<List<Employer>> getAll(){
        var result = employerService.getAll();
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getData(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Getting Employer's detail by defined employer id
     * @param id
     * @return
     */
    @GetMapping("getDetails")
    public ResponseEntity<EmployerInfoDto> getByEmployerId(@RequestParam(name="id") int id){
        var result = employerService.getEmployerInfoById(id);
        if(result.isSuccess()){
            return new ResponseEntity<EmployerInfoDto>(result.getData(),HttpStatus.OK);
        }
        return new ResponseEntity<EmployerInfoDto>(HttpStatus.BAD_REQUEST);
    }
}
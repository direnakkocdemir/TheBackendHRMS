package com.CCT.HRMS.controllers.Users;

import com.CCT.HRMS.business.abstracts.Users.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/staffs/")
public class StaffsController {
    
    // Properties
    private StaffService staffService;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public StaffsController(StaffService staffService) {
        this.staffService = staffService;
    }

//    @PostMapping("add")
//    public ResponseEntity<String> add(@RequestBody Staff staff){
//        var result = staffService.add(staff);
//        if(result.isSuccess()){
//            return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
//        }
//        return new ResponseEntity<>(result.getMessage(),HttpStatus.BAD_REQUEST);
//    }
//
//    @PostMapping("delete")
//    public ResponseEntity<String> delete(@RequestBody Staff staff){
//        var result = staffService.delete(staff);
//        if(result.isSuccess()){
//            return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
//        }
//        return new ResponseEntity<>(result.getMessage(),HttpStatus.BAD_REQUEST);
//    }
//
//    @PostMapping("update")
//    public ResponseEntity<String> update(@RequestBody Staff staff){
//        var result = staffService.update(staff);
//        if(result.isSuccess()){
//            return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
//        }
//        return new ResponseEntity<>(result.getMessage(),HttpStatus.BAD_REQUEST);
//    }
//
//    @GetMapping("get")
//    public ResponseEntity<Staff> getById(@RequestParam int id){
//        var result = staffService.getStaffById(id);
//        if(result.isSuccess()){
//            return new ResponseEntity<>(result.getData(),HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//
//    @GetMapping("getall")
//    public ResponseEntity<List<Staff>> getAll(){
//        var result = staffService.getAll();
//        if(result.isSuccess()){
//            return new ResponseEntity<>(result.getData(),HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

}

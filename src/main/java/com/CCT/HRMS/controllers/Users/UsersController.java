package com.CCT.HRMS.controllers.Users;

import com.CCT.HRMS.business.abstracts.Users.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/users/")
public class UsersController {
    
    // Properties
    private UserService userService;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    // @PostMapping("add")
    // public ResponseEntity<String> add(@RequestBody User user){
    //     var result = userService.add(user);
    //     if(result.isSuccess()){
    //         return new ResponseEntity<String>(result.getMessage(),HttpStatus.OK);
    //     }
    //     return new ResponseEntity<String>(result.getMessage(),HttpStatus.BAD_REQUEST);
    // }

    // @GetMapping("getall")
    // public ResponseEntity<List<User>> getAll(){
    //     var result = userService.getAll();
    //     if(result.isSuccess()){
    //         return new ResponseEntity<List<User>>(result.getData(),HttpStatus.OK);
    //     }
    //     return new ResponseEntity<List<User>>(HttpStatus.BAD_REQUEST);

    // }

}

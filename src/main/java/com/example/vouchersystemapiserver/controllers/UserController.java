package com.example.vouchersystemapiserver.controllers;


import com.example.vouchersystemapiserver.models.User;
import com.example.vouchersystemapiserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return  new ResponseEntity<>("hello world",HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> user(){
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user=userService.findUserByUsername(userDetails.getUsername());
        if (user.isEmpty()){
            return new ResponseEntity<String>("user not found",HttpStatus.OK);
        }
        return new ResponseEntity<User>(user.get(),HttpStatus.OK);
    }
}

package com.microservice.identity.controller;

import com.microservice.identity.entity.User;
import com.microservice.identity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/identity/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getuser/{id}")
    public User getUser(@PathVariable int id){
        return userService.getUserById(id);
    }
}

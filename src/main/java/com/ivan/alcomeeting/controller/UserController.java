package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "{userId}")
    public User getUserById(@PathVariable("userId") Long userId){
        return userService.getUserById(userId);
    }

//    @PostMapping
//    public void addNewUser(@RequestBody User user){
//        userService.saveUser(user);
//    }





}

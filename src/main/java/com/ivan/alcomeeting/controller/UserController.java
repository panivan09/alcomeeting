package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.dto.UserUpdateDto;
import com.ivan.alcomeeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "{userId}")
    public UserDto getUserById(@PathVariable("userId") Long userId){
        return userService.getUserById(userId);
    }

    // CRUD endpoints
    // get all users +
    // create user ??????????????????????????
    // update user +
    // remove user +
    // add beverage (id)
    // remove beverage (id)

    @GetMapping("/bulk")
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping
    public UserDto createUser(@RequestBody UserCreationDto userCreationDto){
        UserDto user = userService.createUser(userCreationDto); // ?????????????
        return userService.getUserById(user.getId());
    }

    /**
     * If this method receive a "userUpdate" with "null" field, it sets "null" to field for existing "User".
     */
    @PutMapping
    public UserDto updateUser(@RequestBody UserUpdateDto userUpdate){
        return userService.updateUser(userUpdate);


    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }



}

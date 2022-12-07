package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.dto.UserUpdateDto;
import com.ivan.alcomeeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('READ')")
    public UserDto getUserById(@PathVariable("userId") Long userId){
        return userService.getUserById(userId);
    }

    // CRUD endpoints
    // get all users +
    // create user ??????????????????????????
    // update user +
    // remove user +
    // add beverage (id) +
    // remove beverage (id) +

    @GetMapping("/bulk")
    @PreAuthorize("hasAuthority('READ')")
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping
    @PreAuthorize("hasAuthority('WRITE')")
    public UserDto createUser(@RequestBody UserCreationDto userCreationDto){
        return userService.createUser(userCreationDto);
    }

    /**
     * If this method receive a "userUpdate" with "null" field, it sets "null" to field for existing "User".
     */
    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE')")
    public UserDto updateUser(@RequestBody UserUpdateDto userUpdate){
        return userService.updateUser(userUpdate);
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAuthority('DELETE')")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @PutMapping( "/{userId}/beverage/")
    @PreAuthorize("hasAuthority('WRITE')")
    public UserDto addBeverage(@PathVariable("userId") Long userId,
                                   @RequestParam Long beverageId) {

        return userService.addBeverage(userId, beverageId);
    }

    @DeleteMapping("/{userId}/beverage/")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public UserDto deleteBeverage(@PathVariable("userId") Long userId,
                                  @RequestParam Long beverageId){

        return userService.deleteBeverage(userId, beverageId);
    }


}

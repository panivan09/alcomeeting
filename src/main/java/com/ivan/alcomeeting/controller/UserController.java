package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.dto.UserUpdateDto;
import com.ivan.alcomeeting.service.userservice.UserDeleteService;
import com.ivan.alcomeeting.service.userservice.UserReadService;
import com.ivan.alcomeeting.service.userservice.UserUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserReadService userReadService;
    private final UserUpdateService userUpdateService;
    private final UserDeleteService userDeleteService;

    @Autowired
    public UserController(UserReadService userReadService,
                          UserUpdateService userUpdateService,
                          UserDeleteService userDeleteService) {
        this.userReadService = userReadService;
        this.userUpdateService = userUpdateService;
        this.userDeleteService = userDeleteService;
    }

    @GetMapping(path = "{userId}")
    @PreAuthorize("hasAuthority('READ')")
    public UserDto getUserById(@PathVariable("userId") Long userId){
        return userReadService.getUserById(userId);
    }

    @GetMapping("/bulk")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> getAllUsers(){
        return userReadService.getAllUsers();
    }

    /**
     * If this method receive a "userUpdate" with "null" field, it sets "null" to field for existing "User".
     */
    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE')")
    public UserDto updateUser(@RequestBody UserUpdateDto userUpdate){
        return userUpdateService.updateUser(userUpdate);
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAuthority('DELETE')")
    public void deleteUser(@PathVariable Long userId){
        userDeleteService.deleteUser(userId);
    }

    @PutMapping( "/{userId}/beverage")
    @PreAuthorize("hasAuthority('WRITE')")
    public UserDto addBeverage(@PathVariable("userId") Long userId,
                                   @RequestParam Long beverageId) {

        return userUpdateService.addBeverage(userId, beverageId);
    }

    @DeleteMapping("/{userId}/beverage")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public UserDto deleteBeverage(@PathVariable("userId") Long userId,
                                  @RequestParam Long beverageId){

        return userUpdateService.deleteBeverage(userId, beverageId);
    }


}

package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.dto.UserUpdateDto;
import com.ivan.alcomeeting.service.user.UserDeleteService;
import com.ivan.alcomeeting.service.user.UserReadService;
import com.ivan.alcomeeting.service.user.UserRightService;
import com.ivan.alcomeeting.service.user.UserUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserReadService userReadService;
    private final UserUpdateService userUpdateService;
    private final UserDeleteService userDeleteService;
    private final UserRightService userRightService;

    @Autowired
    public UserController(UserReadService userReadService,
                          UserUpdateService userUpdateService,
                          UserDeleteService userDeleteService,
                          UserRightService userRightService) {
        this.userReadService = userReadService;
        this.userUpdateService = userUpdateService;
        this.userDeleteService = userDeleteService;
        this.userRightService = userRightService;
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

    @PutMapping("/{userId}/fullname")
    @PreAuthorize("hasAuthority('UPDATE')")
    public UserDto updateUserFullName(@PathVariable("userId") Long userId,
                                      @Valid @RequestBody UserUpdateDto userUpdate,
                                      Principal principal){
        userRightService.validateIsAllowedForUser(principal, userUpdate.getId());

        return userUpdateService.updateUserFullName(userUpdate);
    }

    @PutMapping("/{userId}/password")
    @PreAuthorize("hasAuthority('UPDATE')")
    public UserDto updateUserPassword(@PathVariable("userId") Long userId,
                                      @RequestBody String password,
                                      Principal principal){
        userRightService.validateIsAllowedForUser(principal,userId);

        return userUpdateService.updateUserPassword(userId, password);
    }

    @PutMapping("/{userId}/email")
    @PreAuthorize("hasAuthority('UPDATE')")
    public UserDto updateUserEmail(@PathVariable("userId") Long userId,
                                      @RequestBody String email,
                                      Principal principal){
        userRightService.validateIsAllowedForUser(principal,userId);

        return userUpdateService.updateUserEmail(userId, email);
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAuthority('DELETE')")
    public void deleteUser(@PathVariable Long userId){
        userDeleteService.deleteUser(userId);
    }

    @PutMapping( "/{userId}/beverage")
    @PreAuthorize("hasAuthority('WRITE')")
    public UserDto addBeverage(@PathVariable("userId") Long userId,
                               @RequestParam Long beverageId,
                               Principal principal) {
        userRightService.validateIsAllowedForUser(principal, userId);

        return userUpdateService.addBeverage(userId, beverageId);
    }

    @DeleteMapping("/{userId}/beverage")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public UserDto deleteBeverage(@PathVariable("userId") Long userId,
                                  @RequestParam Long beverageId,
                                  Principal principal){
        userRightService.validateIsAllowedForUser(principal, userId);

        return userUpdateService.deleteBeverage(userId, beverageId);
    }

    // endpoint for update password updateUserPassword(@RequestBodyBoyd String password) - +
    // updateUserFullName(@RequestBody UserFullNameDto fullName) - +
    // updateUserEmail(@RequestBody String email) - +
    // + validation on each of them - +
}

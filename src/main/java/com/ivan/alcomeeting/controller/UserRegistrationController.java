package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.service.user.UserCreationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/registration")
public class UserRegistrationController {
    private final UserCreationService userCreationService;

    public UserRegistrationController(UserCreationService userCreationService) {
        this.userCreationService = userCreationService;
    }

    @PostMapping
    public UserDto registerNewUser(@Valid @RequestBody UserCreationDto userCreationDto) throws ValidationException {
        return userCreationService.createUser(userCreationDto);
    }
}

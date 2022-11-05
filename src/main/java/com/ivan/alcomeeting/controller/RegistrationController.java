package com.ivan.alcomeeting.controller;


import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.service.BeverageService;
import com.ivan.alcomeeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("api/registration")
public class RegistrationController {

    private final BeverageService beverageService;
    private final UserService userService;

    @Autowired
    public RegistrationController(BeverageService beverageService,
                                  UserService userService) {
        this.beverageService = beverageService;
        this.userService = userService;
    }

    @PostMapping
    public String registerUser(@ModelAttribute("userCreationDto") UserCreationDto user){

        userService.createUser(user);

        return "redirect:/main";
    }

}

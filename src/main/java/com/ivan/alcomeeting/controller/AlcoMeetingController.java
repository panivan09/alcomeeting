package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class AlcoMeetingController {

    private final UserService userService;

    @Autowired
    public AlcoMeetingController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(Model model){

        model.addAttribute("allUsers", userService.getAllUsers());

        return "index";
    }
}

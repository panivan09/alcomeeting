package com.ivan.alcomeeting.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class LoginController {

    @GetMapping("login")
    public String showLoginPage(){
        return "login";
    }
}

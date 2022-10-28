package com.ivan.alcomeeting.controller;


import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.service.BeverageService;
import com.ivan.alcomeeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final BeverageService beverageService;
    private final UserService userService;

    @Autowired
    public RegistrationController(BeverageService beverageService,
                                  UserService userService) {
        this.beverageService = beverageService;
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationPage(Model model){

        User newUser = new User();

        model.addAttribute("newUser", newUser);
        model.addAttribute("allBeverages", beverageService.getAllBeverages());

        return "registration";
    }


    @PostMapping
    public String registerUser(@ModelAttribute("newUser") User user){

        userService.saveUser(user);

        return "redirect:/";
    }

}

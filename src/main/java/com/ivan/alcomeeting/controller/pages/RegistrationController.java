package com.ivan.alcomeeting.controller.pages;


import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.service.BeverageService;
import com.ivan.alcomeeting.service.user.UserCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("thyme/registration")
public class RegistrationController {

    private final BeverageService beverageService;
    private final UserCreationService userCreationService;

    @Autowired
    public RegistrationController(BeverageService beverageService,
                                  UserCreationService userCreationService) {
        this.beverageService = beverageService;
        this.userCreationService = userCreationService;
    }

    @GetMapping
    public String showRegistrationPage(Model model){
        UserCreationDto userCreationDto = new UserCreationDto();

        model.addAttribute("userCreationDto", userCreationDto);
        model.addAttribute("allBeverages", beverageService.getAllBeverages());

        return "registration";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("userCreationDto") UserCreationDto user) throws ValidationException {
        userCreationService.createUser(user);

        return "redirect:main";
    }


}

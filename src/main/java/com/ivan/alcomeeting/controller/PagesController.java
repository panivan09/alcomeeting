package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.service.BeverageService;
import com.ivan.alcomeeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PagesController {
    private final BeverageService beverageService;
    private final MeetingService meetingService;

    @Autowired
    public PagesController(BeverageService beverageService, MeetingService meetingService) {
        this.beverageService = beverageService;
        this.meetingService = meetingService;
    }

    @GetMapping(path = "registration")
    public String showRegistrationPage(Model model){

        UserCreationDto userCreationDto = new UserCreationDto();

        model.addAttribute("userCreationDto", userCreationDto);
        model.addAttribute("allBeverages", beverageService.getAllBeverages());

        return "registration";
    }

    @GetMapping(path = "main")
    public String showAllMeetings(Model model){

        model.addAttribute("allMeetings", meetingService.getAllMeetings());

        return "main";
    }
}

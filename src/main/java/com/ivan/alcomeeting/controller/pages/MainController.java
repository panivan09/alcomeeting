package com.ivan.alcomeeting.controller.pages;


import com.ivan.alcomeeting.service.view.MeetingViewService;
import com.ivan.alcomeeting.service.view.UserViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/thyme/main")
public class MainController {
    private final MeetingViewService meetingViewService;
    private final UserViewService userViewService;

    @Autowired
    public MainController(MeetingViewService meetingViewService,
                          UserViewService userViewService) {
        this.meetingViewService = meetingViewService;
        this.userViewService = userViewService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String showAllMeetings(Model model,
                                  Principal principal){
        model.addAttribute("allMeetings", meetingViewService.getAllMeetingForView(principal));
        model.addAttribute("loggedUserFullName", userViewService.getLoggedUserFullName(principal.getName()));

        return "main";
    }






}

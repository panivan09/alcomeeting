package com.ivan.alcomeeting.controller.pages;

import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.service.BeverageService;
import com.ivan.alcomeeting.service.MeetingService;
import com.ivan.alcomeeting.service.view.MeetingViewCreationService;
import com.ivan.alcomeeting.service.view.MeetingViewService;
import com.ivan.alcomeeting.service.view.UserViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/thyme/creatingmeeting")
public class MeetingCreationController {

    private final BeverageService beverageService;
    private final MeetingViewCreationService meetingViewCreationService;
    private final UserViewService userViewService;

    @Autowired
    public MeetingCreationController(BeverageService beverageService,
                                     MeetingViewCreationService meetingViewCreationService,
                                     UserViewService userViewService) {
        this.beverageService = beverageService;
        this.meetingViewCreationService = meetingViewCreationService;
        this.userViewService = userViewService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('WRITE')")
    public String showCreatingMeetingPage(Model model, Principal principal){
        MeetingCreationDto meetingCreationDto = new MeetingCreationDto();

        model.addAttribute("meetingCreationDto", meetingCreationDto);
        model.addAttribute("allBeverages", beverageService.getAllBeverages());
        model.addAttribute("loggedUserFullName", userViewService.getLoggedUserFullName(principal.getName()));

        return "creatingmeeting";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE')")
    public String createMeeting(@ModelAttribute("meetingCreationDto") MeetingCreationDto meeting,
                               Principal principal) throws ValidationException {
        meetingViewCreationService.createMeeting(meeting, principal);

        return "redirect:main";
    }
}

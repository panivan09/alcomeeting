package com.ivan.alcomeeting.controller.pages;

import com.ivan.alcomeeting.service.view.MeetingViewSearchService;
import com.ivan.alcomeeting.service.view.UserViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/thyme/search")
public class MeetingSearchController {

    private final MeetingViewSearchService meetingViewSearchService;
    private final UserViewService userViewService;

    @Autowired
    public MeetingSearchController(MeetingViewSearchService meetingViewSearchService,
                                   UserViewService userViewService) {
        this.meetingViewSearchService = meetingViewSearchService;
        this.userViewService = userViewService;
    }

    @GetMapping(path = "beverage")
    @PreAuthorize("hasAuthority('READ')")
    public String showMeetingsByBeverage(@RequestParam String beverageName,
                                         Model model,
                                         Principal principal){
        model.addAttribute("meetings", meetingViewSearchService.getMeetingsByBeverageName(beverageName, principal));
        model.addAttribute("loggedUserFullName", userViewService.getLoggedUserFullName(principal.getName()));

        return "meetings";
    }

    @GetMapping(path = "date")
    @PreAuthorize("hasAuthority('READ')")
    public String showMeetingsByDate(@RequestParam String date,
                                     Model model,
                                     Principal principal) {
        model.addAttribute("meetings", meetingViewSearchService.getMeetingsByDate(
                LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                principal));
        model.addAttribute("loggedUserFullName", userViewService.getLoggedUserFullName(principal.getName()));

        return "meetings";
    }

    @GetMapping(path = "address")
    @PreAuthorize("hasAuthority('READ')")
    public String showMeetingsByAddress(@RequestParam String address,
                                        Model model,
                                        Principal principal) {
        model.addAttribute("meetings", meetingViewSearchService.getMeetingsByAddress(address, principal));
        model.addAttribute("loggedUserFullName", userViewService.getLoggedUserFullName(principal.getName()));

        return "meetings";
    }
}

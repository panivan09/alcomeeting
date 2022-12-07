package com.ivan.alcomeeting.controller.pages;

import com.ivan.alcomeeting.dto.MeetingUpdateDto;
import com.ivan.alcomeeting.dto.view.MeetingViewDto;
import com.ivan.alcomeeting.service.view.MeetingViewUpdateService;
import com.ivan.alcomeeting.service.view.UserViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/thyme/meeting")
public class MeetingUpdateController {

    private final MeetingViewUpdateService meetingViewUpdateService;
    private final UserViewService userViewService;

    @Autowired
    public MeetingUpdateController(MeetingViewUpdateService meetingViewUpdateService,
                                   UserViewService userViewService) {
        this.meetingViewUpdateService = meetingViewUpdateService;
        this.userViewService = userViewService;
    }

    @GetMapping("update")
    @PreAuthorize("hasAuthority('UPDATE')")
    public String showMeetingToUpdate(@RequestParam Long meetingId,
                                      Model model,
                                      Principal principal){
        MeetingUpdateDto meetingUpdateDto = new MeetingUpdateDto();

        model.addAttribute("meetingUpdateDto", meetingUpdateDto);
        model.addAttribute("meetingById", meetingViewUpdateService.getOwnerMeetingById(meetingId, principal));
        model.addAttribute("loggedUserFullName", userViewService.getLoggedUserFullName(principal.getName()));

        return "updatemeeting";
    }

    @PostMapping("update")
    @PreAuthorize("hasAuthority('UPDATE')")
    public String updateMeeting(@ModelAttribute("meetingUpdateDto") MeetingUpdateDto meetingUpdateDto,
                                Principal principal,
                                RedirectAttributes redirectAttributes){
        MeetingViewDto meetingViewDto = meetingViewUpdateService.updateMeeting(meetingUpdateDto, principal);

        redirectAttributes.addAttribute("meetingId", meetingViewDto.getId());

        return "redirect:update";
    }

    @GetMapping("{meetingId}/add/participate")
    @PreAuthorize("hasAuthority('UPDATE')")
    public String addParticipateToMeeting(@PathVariable("meetingId") Long meetingId,
                                          Principal principal){

        meetingViewUpdateService.addParticipateToMeeting(meetingId, principal);

        return "redirect:/thyme/main";
    }

    @GetMapping("{meetingId}/delete/participate")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String deleteParticipateFromMeeting(@PathVariable("meetingId") Long meetingId,
                                          Principal principal){

        meetingViewUpdateService.deleteParticipateFromMeeting(meetingId, principal);

        return "redirect:/thyme/main";
    }

}

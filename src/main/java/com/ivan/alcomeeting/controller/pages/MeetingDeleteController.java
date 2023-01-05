package com.ivan.alcomeeting.controller.pages;

import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.service.meeting.MeetingDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/thyme/meeting/delete")
public class MeetingDeleteController {
    private final MeetingDeleteService meetingDeleteService;

    @Autowired
    public MeetingDeleteController(MeetingDeleteService meetingDeleteService) {
        this.meetingDeleteService = meetingDeleteService;
    }



    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String deleteMeeting(@RequestParam Long meetingId) throws ValidationException {
        meetingDeleteService.deleteMeeting(meetingId);

        return "redirect:/thyme/main";
    }
}

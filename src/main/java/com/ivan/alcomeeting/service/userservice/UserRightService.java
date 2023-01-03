package com.ivan.alcomeeting.service.userservice;

import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.service.meetingservice.MeetingReadService;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserRightService {
    private final UserReadService userReadService;
    private final MeetingReadService meetingReadService;

    public UserRightService(UserReadService userReadService, MeetingReadService meetingReadService) {
        this.userReadService = userReadService;
        this.meetingReadService = meetingReadService;
    }

    public void isAllowed(Principal principal, Long meetingId){
        Long loggedUserId = userReadService.getUserByUserName(principal.getName()).getId();

        Long meetingOwnerId = meetingReadService.getMeetingById(meetingId).getMeetingOwner().getId();

        if (!loggedUserId.equals(meetingOwnerId)){
            throw new ValidationException("Logged user ID and meeting owner ID don't match");
        }
    }
}

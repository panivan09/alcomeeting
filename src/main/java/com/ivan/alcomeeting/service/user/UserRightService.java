package com.ivan.alcomeeting.service.user;

import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.EntityNotFoundException;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.service.meeting.MeetingReadService;
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

    public void validateIsAllowedForMeeting(Principal principal, Long meetingId){
        try {
            User loggedUser = userReadService.getUserByUserName(principal.getName());
            Long meetingOwnerId = meetingReadService.getMeetingById(meetingId).getMeetingOwner().getId();
            boolean isOwner = loggedUser.getId().equals(meetingOwnerId);

            boolean isAdmin = loggedUser.getRoles().stream()
                    .anyMatch(role -> role.getName().equals("ADMIN"));

            if (!isOwner && !isAdmin) {
                throw new ValidationException("Current user does not have access");
            }
        } catch (EntityNotFoundException e){
            throw new ValidationException("Can't find user or meeting for right validation", e);
        }
    }

    public void validateIsAllowedForUser(Principal principal, Long userId){
        Long loggedUserId = userReadService.getUserByUserName(principal.getName()).getId();

        if (!loggedUserId.equals(userId)){
            throw new ValidationException("Current user does not have access");
        }
    }
}

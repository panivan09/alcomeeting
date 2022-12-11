package com.ivan.alcomeeting.service.view;

import com.ivan.alcomeeting.converter.view.MeetingViewConverter;
import com.ivan.alcomeeting.dto.MeetingUpdateDto;
import com.ivan.alcomeeting.dto.view.MeetingViewDto;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.validation.MeetingUpdateValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class MeetingViewUpdateService {

    private final MeetingRepository meetingRepository;
    private final MeetingViewConverter meetingViewConverter;
    private final UserViewService userViewService;
    private final MeetingUpdateValidation meetingUpdateValidation;

    @Autowired
    public MeetingViewUpdateService(MeetingRepository meetingRepository,
                                    MeetingViewConverter meetingViewConverter,
                                    UserViewService userViewService,
                                    MeetingUpdateValidation meetingUpdateValidation) {
        this.meetingRepository = meetingRepository;
        this.meetingViewConverter = meetingViewConverter;
        this.userViewService = userViewService;
        this.meetingUpdateValidation = meetingUpdateValidation;
    }

    public MeetingViewDto updateMeeting(MeetingUpdateDto meetingUpdateDto, Principal principal) {
        Meeting existMeeting = getMeetingEntityById(meetingUpdateDto.getId());

        meetingUpdateValidation.isValid(meetingUpdateDto, existMeeting);

        existMeeting.setName(meetingUpdateDto.getName());
        existMeeting.setDate(LocalDateTime.parse(meetingUpdateDto.getDate()));
        existMeeting.setAddress(meetingUpdateDto.getAddress());

        meetingRepository.save(existMeeting);

        return meetingViewConverter.meetingToMeetingViewDto(existMeeting, isOwner(principal, existMeeting));
    }

    //TODO: add validation to existing meeting
    public MeetingViewDto addParticipateToMeeting(Long meetingId, Principal principal){
        Meeting existMeeting = getMeetingEntityById(meetingId);

        User participate = userViewService.getUserByUserName(principal.getName());

        existMeeting.getParticipates().add(participate);

        meetingRepository.save(existMeeting);

        return meetingViewConverter.meetingToMeetingViewDto(existMeeting, isOwner(principal, existMeeting));
    }

    //TODO: add validation to existing meeting
    public MeetingViewDto deleteParticipateFromMeeting(Long meetingId, Principal principal){
        Meeting existMeeting = getMeetingEntityById(meetingId);

        User participate = userViewService.getUserByUserName(principal.getName());

        existMeeting.getParticipates().remove(participate);

        meetingRepository.save(existMeeting);

        return meetingViewConverter.meetingToMeetingViewDto(existMeeting, isOwner(principal, existMeeting));
    }

    public MeetingViewDto getOwnerMeetingById(Long meetingId, Principal principal) {
        Meeting meetingEntityById = getMeetingEntityById(meetingId);
        return meetingViewConverter.meetingToMeetingViewDto(meetingEntityById, isOwner(principal, meetingEntityById));
    }

    private Meeting getMeetingEntityById(Long meetingId) {
        Optional<Meeting> meetingEntity = meetingRepository.findById(meetingId);
        if (meetingEntity.isEmpty()) {
            throw new IllegalStateException("Meeting whit id: " + meetingId + " - doesn't exist");
        }
        return meetingEntity.get();
    }

    private boolean isOwner(Principal principal, Meeting meeting) {
        return Objects.equals(meeting.getMeetingOwner().getUserName(), principal.getName());
    }
}
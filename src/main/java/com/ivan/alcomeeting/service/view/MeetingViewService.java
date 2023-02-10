package com.ivan.alcomeeting.service.view;

import com.ivan.alcomeeting.converter.view.MeetingViewConverter;
import com.ivan.alcomeeting.dto.view.MeetingViewDto;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MeetingViewService {

    private final MeetingRepository meetingRepository;
    private final MeetingViewConverter meetingViewConverter;

    @Autowired
    public MeetingViewService(MeetingRepository meetingRepository,
                              MeetingViewConverter meetingViewConverter) {
        this.meetingRepository = meetingRepository;
        this.meetingViewConverter = meetingViewConverter;
    }

    public List<MeetingViewDto> getAllMeetingForView(Principal principal){
        return meetingRepository.findAll().stream()
                .map(e -> meetingViewConverter.meetingToMeetingViewDto(e, isOwner(principal, e)))
                .collect(Collectors.toList());
    }

    private boolean isOwner(Principal principal, Meeting meeting) {
        return Objects.equals(meeting.getMeetingOwner().getUserName(), principal.getName());
    }

}

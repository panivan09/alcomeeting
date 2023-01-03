package com.ivan.alcomeeting.converter.view;


import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.dto.view.MeetingViewDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MeetingViewConverter {

    public MeetingViewDto meetingToMeetingViewDto(Meeting meeting, boolean isOwner) {
        MeetingViewDto meetingViewDto = new MeetingViewDto();

        meetingViewDto.setId(meeting.getId());
        meetingViewDto.setName(meeting.getName());
        meetingViewDto.setDate(meeting.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        meetingViewDto.setAddress(meeting.getAddress());

        User meetingOwner = meeting.getMeetingOwner();
        meetingViewDto.setMeetingOwner(meetingOwner.getName() + " " + meetingOwner.getLastName());
        meetingViewDto.setListParticipates(meeting.getParticipates().stream()
                .map(user -> user.getName() + " " + user.getLastName())
                .collect(Collectors.joining(", ")));
        meetingViewDto.setListBeverages(meeting.getBeverages().stream()
                .map(Beverage::getName)
                .collect(Collectors.joining(", ")));
        meetingViewDto.setOwner(isOwner);

        return meetingViewDto;
    }

    public Meeting meetingCreationDtoToMeeting(MeetingCreationDto meetingCreationDto,
                                               List<Beverage> beverages,
                                               User owner) {

        Meeting meeting = new Meeting();
        Set<User> participates = new HashSet<>();
        participates.add(owner);

        meeting.setName(meetingCreationDto.getName());
        meeting.setDate(LocalDateTime.parse(meetingCreationDto.getDate()));
        meeting.setAddress(meetingCreationDto.getAddress());
        meeting.setMeetingOwner(owner);
        meeting.setParticipates(participates);
        meeting.setBeverages(new HashSet<>(beverages));

        return meeting;
    }
}

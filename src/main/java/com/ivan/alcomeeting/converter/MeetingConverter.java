package com.ivan.alcomeeting.converter;

import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MeetingConverter {

    public final UserConverter userConverter;
    public final BeverageConverter beverageConverter;

    @Autowired
    public MeetingConverter(UserConverter userConverter, BeverageConverter beverageConverter) {
        this.userConverter = userConverter;
        this.beverageConverter = beverageConverter;
    }

    public MeetingDto meetingToMeetingDto(Meeting meeting){

        MeetingDto meetingDto = new MeetingDto();

        meetingDto.setId(meeting.getId());
        meetingDto.setName(meeting.getName());
        meetingDto.setDate(meeting.getDate());
        meetingDto.setAddress(meeting.getAddress());
        meetingDto.setMeetingOwner(userConverter.userToUserDto(meeting.getMeetingOwner()));
        meetingDto.setParticipates(meeting.getParticipates().stream()
                                                            .map(userConverter::userToUserDto)
                                                            .collect(Collectors.toList()));
        meetingDto.setBeveragesDto(meeting.getBeverages().stream()
        .map(beverageConverter::beverageToBeverageDto)
        .collect(Collectors.toList()));

        return meetingDto;
    }


    public Meeting meetingCreationDtoToMeeting(MeetingCreationDto meetingCreationDto) {

        Meeting meeting = new Meeting();

//        meeting.setId(meetingCreationDto.getId());
        meeting.setName(meetingCreationDto.getName());
        meeting.setDate(meetingCreationDto.getDate());
        meeting.setAddress(meetingCreationDto.getAddress());
        meeting.setMeetingOwner(new User(meetingCreationDto.getMeetingOwner()));
        meeting.setBeverages(meetingCreationDto.getBeveragesDto().stream()
                                                                    .map(Beverage::new)
                                                                    .collect(Collectors.toSet()));
        meeting.setParticipates(meetingCreationDto.getParticipates().stream()
                                                                    .map(User::new)
                                                                    .collect(Collectors.toSet()));

        return meeting;

    }
}

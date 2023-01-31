package com.ivan.alcomeeting.converter;

import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        meetingDto.setDate(meeting.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        meetingDto.setAddress(meeting.getAddress());
        meetingDto.setMeetingOwner(userConverter.userToUserDto(meeting.getMeetingOwner()));

        Set<User> participates = meeting.getParticipates();
        if (CollectionUtils.isEmpty(participates)){
            meetingDto.setParticipates(new ArrayList<>());
        } else {
            meetingDto.setParticipates(participates.stream()
                    .map(userConverter::userToUserDto)
                    .collect(Collectors.toList()));
        }

        Set<Beverage> beverages = meeting.getBeverages();
        if (CollectionUtils.isEmpty(beverages)){
            meetingDto.setBeveragesDto(new ArrayList<>());
        } else {
            meetingDto.setBeveragesDto(meeting.getBeverages().stream()
                    .map(beverageConverter::beverageToBeverageDto)
                    .collect(Collectors.toList()));
        }
        return meetingDto;
    }

    public Meeting meetingCreationDtoToMeeting(
            MeetingCreationDto meetingCreationDto,
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

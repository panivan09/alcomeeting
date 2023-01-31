package com.ivan.alcomeeting.converter;

import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MeetingConverterTest {
    @Mock
    public UserConverter userConverter;
    @Mock
    public BeverageConverter beverageConverter;
    @InjectMocks
    private MeetingConverter meetingConverter;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void meetingToMeetingDto_returnMeetingDtoWithEmptyParticipatesAndBeverages() {
        // Given
        Long meetingId = 3L;
        String name = "Ola";
        String address = "Cuba";
        LocalDateTime localDateTime = LocalDateTime.now();

        User user = new User();
        user.setId(1L);
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        Set<User> participates = new HashSet<>();
        Set<Beverage> beverages = new HashSet<>();
        List<UserDto> userDtoList = new ArrayList<>();
        List<BeverageDto> beverageDtoList = new ArrayList<>();

        Meeting meeting = new Meeting(meetingId,
                name,
                localDateTime,
                address,
                user,
                participates,
                beverages);

        MeetingDto expected = new MeetingDto(meetingId,
                name,
                localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                address,
                userDto,
                userDtoList,
                beverageDtoList);

        when(userConverter.userToUserDto(user)).thenReturn(userDto);

        // When
        MeetingDto actual = meetingConverter.meetingToMeetingDto(meeting);

        // Then
        // TODO: how to use compare?
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void meetingToMeetingDto_returnMeetingDtoWithParticipatesAndBeverages() {
        // Given
        Long meetingId = 3L;
        String name = "Ola";
        String address = "Cuba";
        LocalDateTime localDateTime = LocalDateTime.now();

        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);

        Beverage beverage1 = new Beverage();
        beverage1.setId(2L);
        BeverageDto beverageDto1 = new BeverageDto();
        beverageDto1.setId(2L);
        Beverage beverage2 = new Beverage();
        beverage2.setId(4L);
        BeverageDto beverageDto2 = new BeverageDto();
        beverageDto2.setId(4L);

        Set<User> participates = new HashSet<>();
        participates.add(user1);
        participates.add(user2);

        Set<Beverage> beverages = new HashSet<>();
        beverages.add(beverage1);
        beverages.add(beverage2);

        Meeting meeting = new Meeting(meetingId,
                name,
                localDateTime,
                address,
                user1,
                participates,
                beverages);

        MeetingDto expected = new MeetingDto(meetingId,
                name,
                localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                address,
                userDto1,
                List.of(userDto1,userDto2),
                List.of(beverageDto1, beverageDto2));

        when(userConverter.userToUserDto(user1)).thenReturn(userDto1);
        when(userConverter.userToUserDto(user2)).thenReturn(userDto2);
        when(beverageConverter.beverageToBeverageDto(beverage1)).thenReturn(beverageDto1);
        when(beverageConverter.beverageToBeverageDto(beverage2)).thenReturn(beverageDto2);

        // When
        MeetingDto actual = meetingConverter.meetingToMeetingDto(meeting);

        // Then
        // TODO: how to use compare?
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void meetingToMeetingDto_returnMeetingDtoWithParticipatesAndEmptyBeverages() {
        // Given
        Long meetingId = 3L;
        String name = "Ola";
        String address = "Cuba";
        LocalDateTime localDateTime = LocalDateTime.now();

        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);

        Set<User> participates = new HashSet<>();
        participates.add(user1);
        participates.add(user2);

        Set<Beverage> beverages = new HashSet<>();
        List<BeverageDto> beverageList = new ArrayList<>();

        Meeting meeting = new Meeting(meetingId,
                name,
                localDateTime,
                address,
                user1,
                participates,
                beverages);

        MeetingDto expected = new MeetingDto(meetingId,
                name,
                localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                address,
                userDto1,
                List.of(userDto1,userDto2),
                beverageList);

        when(userConverter.userToUserDto(user1)).thenReturn(userDto1);
        when(userConverter.userToUserDto(user2)).thenReturn(userDto2);

        // When
        MeetingDto actual = meetingConverter.meetingToMeetingDto(meeting);

        // Then
        // TODO: how to use compare?
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void meetingToMeetingDto_returnMeetingDtoWithBeveragesAndEmptyParticipates() {
        // Given
        Long meetingId = 3L;
        String name = "Ola";
        String address = "Cuba";
        LocalDateTime localDateTime = LocalDateTime.now();

        User user1 = new User();
        user1.setId(1L);
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);

        Beverage beverage1 = new Beverage();
        beverage1.setId(2L);
        BeverageDto beverageDto1 = new BeverageDto();
        beverageDto1.setId(2L);
        Beverage beverage2 = new Beverage();
        beverage2.setId(4L);
        BeverageDto beverageDto2 = new BeverageDto();
        beverageDto2.setId(4L);

        Set<User> participates = new HashSet<>();
        List<UserDto> userDtoList = new ArrayList<>();

        Set<Beverage> beverages = new HashSet<>();
        beverages.add(beverage1);
        beverages.add(beverage2);

        Meeting meeting = new Meeting(meetingId,
                name,
                localDateTime,
                address,
                user1,
                participates,
                beverages);

        MeetingDto expected = new MeetingDto(meetingId,
                name,
                localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                address,
                userDto1,
                userDtoList,
                List.of(beverageDto1, beverageDto2));

        when(userConverter.userToUserDto(user1)).thenReturn(userDto1);
        when(beverageConverter.beverageToBeverageDto(beverage1)).thenReturn(beverageDto1);
        when(beverageConverter.beverageToBeverageDto(beverage2)).thenReturn(beverageDto2);

        // When
        MeetingDto actual = meetingConverter.meetingToMeetingDto(meeting);

        // Then
        // TODO: how to use compare?
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void meetingCreationDtoToMeeting_returnMeetingAfterConversion() {
        // Given
        String name = "Ola";
        String address = "Cuba";
        String date = "2023-01-31T15:24:25";

        Long meetingOwnerId = 1L;
        User meetingOwner = new User();
        meetingOwner.setId(1L);

        Long beverageOneId = 2L;
        Long beverageTwoId = 4L;
        Set<Long> beverageIdSet = new HashSet<>();
        beverageIdSet.add(beverageOneId);
        beverageIdSet.add(beverageTwoId);

        Beverage beverage1 = new Beverage();
        beverage1.setId(beverageOneId);
        Beverage beverage2 = new Beverage();
        beverage2.setId(beverageTwoId);
        Set<Beverage> beveragesSet = new HashSet<>();
        beveragesSet.add(beverage1);
        beveragesSet.add(beverage2);
        List<Beverage> beverageList = new ArrayList<>();
        beverageList.add(beverage1);
        beverageList.add(beverage2);

        Set<User> participates = new HashSet<>();
        participates.add(meetingOwner);

        MeetingCreationDto meetingCreationDto = new MeetingCreationDto();
        meetingCreationDto.setName(name);
        meetingCreationDto.setDate(date);
        meetingCreationDto.setAddress(address);
        meetingCreationDto.setMeetingOwner(meetingOwnerId);
        meetingCreationDto.setBeverages(beverageIdSet);

        Meeting expected = new Meeting();
        expected.setName(name);
        expected.setDate(LocalDateTime.parse(date));
        expected.setAddress(address);
        expected.setMeetingOwner(meetingOwner);
        expected.setParticipates(participates);
        expected.setBeverages(beveragesSet);

        // When
        Meeting actual = meetingConverter.meetingCreationDtoToMeeting(meetingCreationDto, beverageList, meetingOwner);

        // Then
        // TODO: is it right approach to compare?
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
package com.ivan.alcomeeting.service.meeting;

import com.ivan.alcomeeting.converter.MeetingConverter;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.dto.MeetingUpdateDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.service.user.UserReadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MeetingUpdateServiceTest {
    @Mock
    private MeetingRepository meetingRepository;
    @Mock
    private MeetingConverter meetingConverter;
    @Mock
    private MeetingReadService meetingReadService;
    @Mock
    private UserReadService userReadService;
    @InjectMocks
    private MeetingUpdateService meetingUpdateService;

    @BeforeEach
    public void beforeTests(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateMeeting_returnMeetingDtoWithUpdatedMeetingName() {
        //Given
        Long meetingId = 3L;
        String meetingName = "Tuc-tuc";

        MeetingUpdateDto meetingUpdateDto = new MeetingUpdateDto();
        meetingUpdateDto.setId(meetingId);
        meetingUpdateDto.setName(meetingName);

        Meeting existedMeeting = new Meeting();
        existedMeeting.setId(meetingId);
        existedMeeting.setName("ola-ola");

        MeetingDto expectedMeetingDto = new MeetingDto();
        expectedMeetingDto.setId(meetingId);
        expectedMeetingDto.setName(meetingName);

        when(meetingReadService.getMeetingEntityById(meetingId)).thenReturn(existedMeeting);
        when(meetingConverter.meetingToMeetingDto(existedMeeting)).thenReturn(expectedMeetingDto);

        //When
        MeetingDto actualMeetingDto = meetingUpdateService.updateMeeting(meetingUpdateDto);

        //Then
        verify(meetingRepository, times(1)).save(existedMeeting);
        assertThat(actualMeetingDto).isEqualTo(expectedMeetingDto);
    }

    @Test
    void updateMeeting_returnMeetingDtoWithUpdatedMeetingDate() {
        //Given
        Long meetingId = 3L;
        String dateToUpdate = "2020-11-10T18:30";
        String date = "2020-11-10 18:30";

        MeetingUpdateDto meetingUpdateDto = new MeetingUpdateDto();
        meetingUpdateDto.setId(meetingId);
        meetingUpdateDto.setDate(dateToUpdate);

        Meeting existedMeeting = new Meeting();
        existedMeeting.setId(meetingId);
        existedMeeting.setDate(LocalDateTime.now());

        MeetingDto expectedMeetingDto = new MeetingDto();
        expectedMeetingDto.setId(meetingId);
        expectedMeetingDto.setDate(date);

        when(meetingReadService.getMeetingEntityById(meetingId)).thenReturn(existedMeeting);
        when(meetingConverter.meetingToMeetingDto(existedMeeting)).thenReturn(expectedMeetingDto);

        //When
        MeetingDto actualMeetingDto = meetingUpdateService.updateMeeting(meetingUpdateDto);

        //Then
        verify(meetingRepository, times(1)).save(existedMeeting);
        assertThat(actualMeetingDto).isEqualTo(expectedMeetingDto);
    }

    @Test
    void updateMeeting_returnMeetingDtoWithUpdatedMeetingAddress() {
        //Given
        Long meetingId = 3L;
        String address = "Mars";

        MeetingUpdateDto meetingUpdateDto = new MeetingUpdateDto();
        meetingUpdateDto.setId(meetingId);
        meetingUpdateDto.setAddress(address);

        Meeting existedMeeting = new Meeting();
        existedMeeting.setId(meetingId);
        existedMeeting.setAddress("Cuba");

        MeetingDto expectedMeetingDto = new MeetingDto();
        expectedMeetingDto.setId(meetingId);
        expectedMeetingDto.setAddress(address);

        when(meetingReadService.getMeetingEntityById(meetingId)).thenReturn(existedMeeting);
        when(meetingConverter.meetingToMeetingDto(existedMeeting)).thenReturn(expectedMeetingDto);

        //When
        MeetingDto actualMeetingDto = meetingUpdateService.updateMeeting(meetingUpdateDto);

        //Then
        verify(meetingRepository, times(1)).save(existedMeeting);
        assertThat(actualMeetingDto).isEqualTo(expectedMeetingDto);
    }

    @Test
    void updateMeeting_returnUpdatedMeetingDto() {
        //Given
        Long meetingId = 3L;
        String meetingName = "Tuc-tuc";
        String dateToUpdate = "2020-11-10T18:30";
        String date = "2020-11-10 18:30";
        String address = "Mars";

        MeetingUpdateDto meetingUpdateDto = new MeetingUpdateDto();
        meetingUpdateDto.setId(meetingId);
        meetingUpdateDto.setName(meetingName);
        meetingUpdateDto.setDate(dateToUpdate);
        meetingUpdateDto.setAddress(address);

        Meeting existedMeeting = new Meeting();
        existedMeeting.setId(meetingId);
        existedMeeting.setName(meetingName);
        existedMeeting.setDate(LocalDateTime.now());
        existedMeeting.setAddress("Cuba");

        MeetingDto expectedMeetingDto = new MeetingDto();
        expectedMeetingDto.setId(meetingId);
        expectedMeetingDto.setName(meetingName);
        expectedMeetingDto.setDate(date);
        expectedMeetingDto.setAddress(address);

        when(meetingReadService.getMeetingEntityById(meetingId)).thenReturn(existedMeeting);
        when(meetingConverter.meetingToMeetingDto(existedMeeting)).thenReturn(expectedMeetingDto);

        //When
        MeetingDto actualMeetingDto = meetingUpdateService.updateMeeting(meetingUpdateDto);

        //Then
        verify(meetingRepository, times(1)).save(existedMeeting);
        assertThat(actualMeetingDto).isEqualTo(expectedMeetingDto);
    }

    @Test
    void addUser_returnMeetingDtoWithAddedUser() {
        //Given
        Long meetingId = 3L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        UserDto userDto = new UserDto();
        userDto.setId(userId);

        Meeting meeting = new Meeting();
        meeting.setId(meetingId);
        meeting.setParticipates(new HashSet<>());

        MeetingDto expected = new MeetingDto();
        expected.setId(meetingId);
        expected.setParticipates(List.of(userDto));

        when(meetingReadService.getMeetingEntityById(meetingId)).thenReturn(meeting);
        when(userReadService.getUserEntityById(userId)).thenReturn(user);
        when(meetingConverter.meetingToMeetingDto(meeting)).thenReturn(expected);

        //When
        MeetingDto actual = meetingUpdateService.addUser(meetingId, userId);

        //Then
        verify(meetingRepository, times(1)).save(meeting);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteUser_returnMeetingDtoWithoutUser() {
        //Given
        Long meetingId = 3L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Set<User> participates = new HashSet<>();
        participates.add(user);

        Meeting meeting = new Meeting();
        meeting.setId(meetingId);
        meeting.setParticipates(participates);

        MeetingDto expected = new MeetingDto();
        expected.setId(meetingId);
        expected.setParticipates(List.of());

        when(meetingReadService.getMeetingEntityById(meetingId)).thenReturn(meeting);
        when(userReadService.getUserEntityById(userId)).thenReturn(user);
        when(meetingConverter.meetingToMeetingDto(meeting)).thenReturn(expected);

        //When
        MeetingDto actual = meetingUpdateService.addUser(meetingId, userId);

        //Then
        verify(meetingRepository, times(1)).save(meeting);
        assertThat(actual).isEqualTo(expected);
    }
}
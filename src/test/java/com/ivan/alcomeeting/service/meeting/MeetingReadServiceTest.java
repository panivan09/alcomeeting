package com.ivan.alcomeeting.service.meeting;

import com.ivan.alcomeeting.converter.MeetingConverter;
import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.exception.EntityNotFoundException;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.service.BeverageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


class MeetingReadServiceTest {
    @Mock
    private MeetingRepository meetingRepository;
    @Mock
    private MeetingConverter meetingConverter;
    @Mock
    private BeverageService beverageService;

    @InjectMocks
    private MeetingReadService meetingReadService;

    @BeforeEach
    public void beforeTests(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMeetingById_returnMeetingDto() {
        //Given
        Long meetingId = 3L;
        Meeting meeting = new Meeting(meetingId,
                "Any",
                null,
                "Any",
                null,
                null,
                null);

        MeetingDto expectedMeetingDto = new MeetingDto(meetingId,
                "Any",
                null,
                "Any",
                null,
                null,
                null);

        when(meetingRepository.findById(meetingId)).thenReturn(Optional.of(meeting));
        when(meetingConverter.meetingToMeetingDto(meeting)).thenReturn(expectedMeetingDto);

        //When
        MeetingDto actualMeetingDto = meetingReadService.getMeetingById(meetingId);

        //Then
        assertThat(actualMeetingDto).isEqualTo(expectedMeetingDto);
    }

    @Test
    void getAllMeetings_returnListMeetingsDto() {
        //Given
        Meeting meeting1 = new Meeting();
        Meeting meeting2 = new Meeting();
        MeetingDto meetingDto1= new MeetingDto();
        MeetingDto meetingDto2 = new MeetingDto();
        List<MeetingDto> expectedList = List.of(meetingDto1, meetingDto2);

        when(meetingConverter.meetingToMeetingDto(meeting1)).thenReturn(meetingDto1);
        when(meetingConverter.meetingToMeetingDto(meeting2)).thenReturn(meetingDto2);
        when(meetingRepository.findAll()).thenReturn(List.of(meeting1, meeting2));

        //When
        List<MeetingDto> actualList = meetingReadService.getAllMeetings();

        //Then
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void getAllMeetingsByBeverage_returnListMeetingDtoWithBeverage() {
        //Given
        Long beverageId = 1L;
        Beverage beverage = new Beverage();
        beverage.setId(beverageId);
        BeverageDto beverageDto = new BeverageDto();
        beverageDto.setId(beverageId);

        Meeting meeting1 = new Meeting();
        meeting1.setBeverages(Set.of(beverage));
        Meeting meeting2 = new Meeting();
        meeting2.setBeverages(Set.of(beverage));

        MeetingDto meetingDto1 = new MeetingDto();
        meetingDto1.setBeveragesDto(List.of(beverageDto));
        MeetingDto meetingDto2 = new MeetingDto();
        meetingDto2.setBeveragesDto(List.of(beverageDto));

//   Or:
//        Meeting meeting1 = new Meeting(1L,
//                "Any",
//                null,
//                "Any",
//                null,
//                null,
//                Set.of(beverage));
//        Meeting meeting2 = new Meeting(2L,
//                "Any",
//                null,
//                "Any",
//                null,
//                null,
//                Set.of(beverage));
//
//        MeetingDto meetingDto1 = new MeetingDto(1L,
//                "Any",
//                null,
//                "Any",
//                null,
//                null,
//                List.of(beverageDto));
//        MeetingDto meetingDto2 = new MeetingDto(2L,
//                "Any",
//                null,
//                "Any",
//                null,
//                null,
//                List.of(beverageDto));

        List<MeetingDto> expectedList = List.of(meetingDto1, meetingDto2);

        when(beverageService.getBeverageEntityById(beverageId)).thenReturn(beverage);
        when(meetingConverter.meetingToMeetingDto(meeting1)).thenReturn(meetingDto1);
        when(meetingConverter.meetingToMeetingDto(meeting2)).thenReturn(meetingDto2);
        when(meetingRepository.findAllByBeverage(beverageId)).thenReturn(List.of(meeting1,meeting2));

        //When
        List<MeetingDto> actualListMeetingDto = meetingReadService.getAllMeetingsByBeverage(beverageId);

        //Then
        assertThat(actualListMeetingDto).isEqualTo(expectedList);
    }

    @Test
    void getAllMeetingsByDate_returnListMeetingDtoByDate() {
        //Given
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate date = dateTime.toLocalDate();

        Meeting meeting1 = new Meeting();
        meeting1.setDate(dateTime);
        Meeting meeting2 = new Meeting();
        meeting2.setDate(dateTime);

        MeetingDto meetingDto1 = new MeetingDto();
        meetingDto1.setDate(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        MeetingDto meetingDto2 = new MeetingDto();
        meetingDto2.setDate(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));


        List<MeetingDto> expectedListMeetingDto = List.of(meetingDto1, meetingDto2);

        when(meetingRepository.findAllByDateBetween(date.atTime(LocalTime.MIN),
                                                    date.atTime(LocalTime.MAX)))
                .thenReturn(List.of(meeting1,meeting2));
        when(meetingConverter.meetingToMeetingDto(meeting1)).thenReturn(meetingDto1);
        when(meetingConverter.meetingToMeetingDto(meeting2)).thenReturn(meetingDto2);

        //When
        List<MeetingDto> actualListMeetingDto = meetingReadService.getAllMeetingsByDate(date);

        //Then
        assertThat(actualListMeetingDto).isEqualTo(expectedListMeetingDto);
    }

    @Test
    void getALlMeetingsByAddress_returnListMeetingDtoByAddress() {
        //Given
        String address = "Mars";

        Meeting meeting1 = new Meeting();
        meeting1.setAddress(address);
        Meeting meeting2 = new Meeting();
        meeting2.setAddress(address);

        MeetingDto meetingDto1 = new MeetingDto();
        meetingDto1.setAddress(address);
        MeetingDto meetingDto2 = new MeetingDto();
        meetingDto2.setAddress(address);

        List<MeetingDto> expectedListMeetingDto = List.of(meetingDto1, meetingDto2);

        when(meetingRepository.findAllByAddress(address)).thenReturn(List.of(meeting1,meeting2));
        when(meetingConverter.meetingToMeetingDto(meeting1)).thenReturn(meetingDto1);
        when(meetingConverter.meetingToMeetingDto(meeting2)).thenReturn(meetingDto2);

        //When
        List<MeetingDto> actualListMeetingDto = meetingReadService.getALlMeetingsByAddress(address);

        //Then
        assertThat(actualListMeetingDto).isEqualTo(expectedListMeetingDto);
    }

    @Test
    void getMeetingEntityById_throwEntityNotFoundExceptionIfMeetingRepositoryHasNoMeeting() {
        //Given
        Long meetingId = 3L;
        String exceptionMessage = "Meeting whit id: " + meetingId + " - doesn't exist";

        when(meetingRepository.findById(meetingId)).thenThrow(new EntityNotFoundException(exceptionMessage));

        //When and Then
        assertThatThrownBy(()-> meetingReadService.getMeetingEntityById(meetingId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void getMeetingEntityById_returnMeetingEntity() {
        //Given
        Long meetingId = 3L;

        Meeting expectedMeeting = new Meeting();
        expectedMeeting.setId(meetingId);

        when(meetingRepository.findById(meetingId)).thenReturn(Optional.of(expectedMeeting));

        //When
        Meeting actualMeeting = meetingReadService.getMeetingEntityById(meetingId);

        //Then
        assertThat(actualMeeting).isEqualTo(expectedMeeting);
    }
}
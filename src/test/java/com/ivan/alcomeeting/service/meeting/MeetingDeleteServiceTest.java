package com.ivan.alcomeeting.service.meeting;

import com.ivan.alcomeeting.repository.MeetingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MeetingDeleteServiceTest {
    @Mock
    private MeetingRepository meetingRepository;
    @InjectMocks
    private MeetingDeleteService meetingDeleteService;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteMeeting_checkThatMeetingWasDeleted() {
        //Given
        Long meetingId = 3L;

        //When
        meetingDeleteService.deleteMeeting(meetingId);

        //Then
        verify(meetingRepository, times(1)).deleteMeetingFromMeetingsUsers(meetingId);
        verify(meetingRepository, times(1)).deleteMeetingFromMeetingsBeverages(meetingId);
        verify(meetingRepository, times(1)).deleteById(meetingId);
    }
}
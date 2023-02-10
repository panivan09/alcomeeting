package com.ivan.alcomeeting.service.meeting;

import com.ivan.alcomeeting.repository.MeetingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeetingDeleteService {
    private final MeetingRepository meetingRepository;

    public MeetingDeleteService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Transactional
    public void deleteMeeting(Long meetingId) {
        meetingRepository.deleteMeetingFromMeetingsUsers(meetingId);

        meetingRepository.deleteMeetingFromMeetingsBeverages(meetingId);

        meetingRepository.deleteById(meetingId);
    }
}

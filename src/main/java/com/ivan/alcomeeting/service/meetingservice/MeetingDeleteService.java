package com.ivan.alcomeeting.service.meetingservice;

import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.validation.view.MeetingDeleteValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeetingDeleteService {
    private final MeetingRepository meetingRepository;
    private final MeetingDeleteValidator meetingDeleteValidator;

    public MeetingDeleteService(MeetingRepository meetingRepository, MeetingDeleteValidator meetingDeleteValidator) {
        this.meetingRepository = meetingRepository;
        this.meetingDeleteValidator = meetingDeleteValidator;
    }

    @Transactional
    public void deleteMeeting(Long meetingId) throws ValidationException {
        meetingDeleteValidator.validate(meetingId);

        meetingRepository.deleteMeetingFromMeetingsUsers(meetingId);

        meetingRepository.deleteMeetingFromMeetingsBeverages(meetingId);

        meetingRepository.deleteById(meetingId);
    }

}

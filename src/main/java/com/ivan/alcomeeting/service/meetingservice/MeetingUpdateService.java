package com.ivan.alcomeeting.service.meetingservice;

import com.ivan.alcomeeting.converter.MeetingConverter;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.dto.MeetingUpdateDto;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.service.userservice.UserReadService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class MeetingUpdateService {
    private final MeetingRepository meetingRepository;
    private final MeetingConverter meetingConverter;
    private final MeetingReadService meetingReadService;
    private final UserReadService userReadService;

    public MeetingUpdateService(MeetingRepository meetingRepository,
                                MeetingConverter meetingConverter,
                                MeetingReadService meetingReadService,
                                UserReadService userReadService) {
        this.meetingRepository = meetingRepository;
        this.meetingConverter = meetingConverter;
        this.meetingReadService = meetingReadService;
        this.userReadService = userReadService;
    }

    public MeetingDto updateMeeting(MeetingUpdateDto meetingUpdateDto) {
        Meeting existMeeting = meetingReadService.getMeetingEntityById(meetingUpdateDto.getId());

        setMeetingFieldsNotNull(meetingUpdateDto, existMeeting);

        existMeeting.setName(meetingUpdateDto.getName());
        existMeeting.setDate(LocalDateTime.parse(meetingUpdateDto.getDate()));
        existMeeting.setAddress(meetingUpdateDto.getAddress());

        meetingRepository.save(existMeeting);

        return meetingConverter.meetingToMeetingDto(existMeeting);
    }

    public MeetingDto addUser(Long meetingId, Long userId) {
        Meeting currentMeeting = meetingReadService.getMeetingEntityById(meetingId);
        User userToAdd = userReadService.getUserEntityById(userId);

        currentMeeting.getParticipates().add(userToAdd);
        meetingRepository.save(currentMeeting);

        return meetingConverter.meetingToMeetingDto(currentMeeting);
    }

    public MeetingDto deleteUser(Long meetingId, Long userId) {
        Meeting currentMeeting = meetingReadService.getMeetingEntityById(meetingId);
        User userToAdd = userReadService.getUserEntityById(userId);

        currentMeeting.getParticipates().remove(userToAdd);
        meetingRepository.save(currentMeeting);

        return meetingConverter.meetingToMeetingDto(currentMeeting);
    }

    private void setMeetingFieldsNotNull(MeetingUpdateDto meetingUpdateDto, Meeting existMeeting) {
        String meetingName = meetingUpdateDto.getName();
        String meetingDate = meetingUpdateDto.getDate();
        String meetingAddress = meetingUpdateDto.getAddress();

        if (meetingName == null || meetingName.isEmpty()){
            meetingUpdateDto.setName(existMeeting.getName());
        }

        if (meetingDate == null || meetingDate.isEmpty()){
            String format = existMeeting.getDate().format(DateTimeFormatter.ISO_DATE_TIME);
            meetingUpdateDto.setDate(format);
        }

        if (meetingAddress == null || meetingAddress.isEmpty()){
            meetingUpdateDto.setAddress(existMeeting.getAddress());
        }
    }
}

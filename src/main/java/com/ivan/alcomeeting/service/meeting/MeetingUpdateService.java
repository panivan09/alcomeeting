package com.ivan.alcomeeting.service.meeting;

import com.ivan.alcomeeting.converter.MeetingConverter;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.dto.MeetingUpdateDto;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.service.user.UserReadService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


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
        Meeting existedMeeting = meetingReadService.getMeetingEntityById(meetingUpdateDto.getId());

        setMeetingFields(meetingUpdateDto, existedMeeting);

        meetingRepository.save(existedMeeting);

        return meetingConverter.meetingToMeetingDto(existedMeeting);
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

    private void setMeetingFields(MeetingUpdateDto meetingUpdateDto, Meeting existMeeting) {
        String meetingName = meetingUpdateDto.getName();
        String meetingDate = meetingUpdateDto.getDate();
        String meetingAddress = meetingUpdateDto.getAddress();

        if (meetingName != null && !meetingName.isEmpty()){
            existMeeting.setName(meetingName);
        }

        if (meetingDate != null && !meetingDate.isEmpty()){
            existMeeting.setDate(LocalDateTime.parse(meetingDate));
        }

        if (meetingAddress != null && !meetingAddress.isEmpty()){
            existMeeting.setAddress(meetingAddress);
        }
    }
}

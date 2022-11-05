package com.ivan.alcomeeting.service;

import com.ivan.alcomeeting.converter.MeetingConverter;
import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.dto.MeetingUpdateDto;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingConverter meetingConverter;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository,
                          MeetingConverter meetingConverter) {
        this.meetingRepository = meetingRepository;
        this.meetingConverter = meetingConverter;
    }


    public List<MeetingDto> getAllMeetings(){
        return meetingRepository.findAll().stream()
                .map(meetingConverter::meetingToMeetingDto)
                .collect(Collectors.toList());
    }


    public MeetingDto getMeetingById(Long meetingId) {
        return meetingConverter.meetingToMeetingDto(getMeetingEntityById(meetingId));
    }

    public MeetingDto createMeeting(MeetingCreationDto meetingCreationDto) {

        Meeting createMeeting = meetingConverter.meetingCreationDtoToMeeting(meetingCreationDto);

        meetingRepository.save(createMeeting);

        return meetingConverter.meetingToMeetingDto(createMeeting);
    }


    public MeetingDto updateMeeting(MeetingUpdateDto meetingUpdateDto) {

        Meeting existMeeting = getMeetingEntityById(meetingUpdateDto.getId());

        existMeeting.setName(meetingUpdateDto.getName());
        existMeeting.setDate(meetingUpdateDto.getDate());
        existMeeting.setAddress(meetingUpdateDto.getAddress());

        meetingRepository.save(existMeeting);

        return meetingConverter.meetingToMeetingDto(existMeeting);
    }

    @Transactional
    public void deleteMeeting(Long meetingId) {

        meetingRepository.deleteMeetingFromMeetingsUsers(meetingId);

        meetingRepository.deleteMeetingFromMeetingsBeverages(meetingId);

        meetingRepository.deleteById(meetingId);
    }


    private Meeting getMeetingEntityById(Long meetingId) {
        Optional<Meeting> meetingEntity = meetingRepository.findById(meetingId);
        if (meetingEntity.isEmpty()){
            throw new IllegalStateException("Meeting whit id: " + meetingId + " - doesn't exist");
        }
        return meetingEntity.get();
    }
}

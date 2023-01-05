package com.ivan.alcomeeting.service.meeting;

import com.ivan.alcomeeting.converter.MeetingConverter;
import com.ivan.alcomeeting.dto.*;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.exception.EntityNotFoundException;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.service.BeverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeetingReadService {
    private final MeetingRepository meetingRepository;
    private final MeetingConverter meetingConverter;
    private final BeverageService beverageService;

    @Autowired
    public MeetingReadService(MeetingRepository meetingRepository,
                              MeetingConverter meetingConverter,
                              BeverageService beverageService) {
        this.meetingRepository = meetingRepository;
        this.meetingConverter = meetingConverter;
        this.beverageService = beverageService;
    }

    public MeetingDto getMeetingById(Long meetingId) {
        Meeting meetingEntity = getMeetingEntityById(meetingId);

        return meetingConverter.meetingToMeetingDto(meetingEntity);
    }

    public List<MeetingDto> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(meetingConverter::meetingToMeetingDto)
                .collect(Collectors.toList());
    }

    public List<MeetingDto> getAllMeetingsByBeverage(Long beverageId) {
        Beverage beverage = beverageService.getBeverageEntityById(beverageId);

        return meetingRepository.findAllByBeverage(beverage.getId()).stream()
                .map(meetingConverter::meetingToMeetingDto)
                .collect(Collectors.toList());
    }

    public List<MeetingDto> getAllMeetingsByDate(LocalDate date) {
        return meetingRepository.findAllByDateBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
            ).stream()
                .map(meetingConverter::meetingToMeetingDto)
                .collect(Collectors.toList());
    }

    public List<MeetingDto> getALlMeetingsByAddress(String address) {
        return meetingRepository.findAllByAddress(address).stream()
                .map(meetingConverter::meetingToMeetingDto)
                .collect(Collectors.toList());
    }

    public Meeting getMeetingEntityById(Long meetingId) {
        Optional<Meeting> meetingEntity = meetingRepository.findById(meetingId);
        if (meetingEntity.isEmpty()) {
            throw new EntityNotFoundException("Meeting whit id: " + meetingId + " - doesn't exist");
        }
        return meetingEntity.get();
    }



}

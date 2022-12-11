package com.ivan.alcomeeting.service;

import com.ivan.alcomeeting.converter.MeetingConverter;
import com.ivan.alcomeeting.dto.*;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.repository.BeverageRepository;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.validation.MeetingDeleteValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingConverter meetingConverter;
    private final UserService userService;
    private final BeverageService beverageService;
    private final BeverageRepository beverageRepository;
    private final MeetingDeleteValidation meetingDeleteValidation;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository,
                          MeetingConverter meetingConverter,
                          UserService userService,
                          BeverageService beverageService,
                          BeverageRepository beverageRepository,
                          MeetingDeleteValidation meetingDeleteValidation) {
        this.meetingRepository = meetingRepository;
        this.meetingConverter = meetingConverter;
        this.userService = userService;
        this.beverageService = beverageService;
        this.beverageRepository = beverageRepository;
        this.meetingDeleteValidation = meetingDeleteValidation;
    }

    public MeetingDto getMeetingById(Long meetingId) {
        return meetingConverter.meetingToMeetingDto(getMeetingEntityById(meetingId));
    }

    public List<MeetingDto> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(meetingConverter::meetingToMeetingDto)
                .collect(Collectors.toList());
    }

    public MeetingDto createMeeting(MeetingCreationDto meetingCreationDto, Principal principal) {
        List<Beverage> allBeverages = meetingCreationDto.getBeverages().stream()
                .map(beverageRepository::getReferenceById)
                .collect(Collectors.toList());

        User owner = userService.getUserByUserName(principal.getName());

        Meeting createMeeting = meetingConverter.meetingCreationDtoToMeeting(
                meetingCreationDto,
                allBeverages,
                owner);

        meetingRepository.save(createMeeting);

        return meetingConverter.meetingToMeetingDto(createMeeting);

    }


    public MeetingDto updateMeeting(MeetingUpdateDto meetingUpdateDto) {
        Meeting existMeeting = getMeetingEntityById(meetingUpdateDto.getId());

        existMeeting.setName(meetingUpdateDto.getName());
        existMeeting.setDate(LocalDateTime.parse(meetingUpdateDto.getDate()));
        existMeeting.setAddress(meetingUpdateDto.getAddress());

        meetingRepository.save(existMeeting);

        return meetingConverter.meetingToMeetingDto(existMeeting);
    }


    @Transactional
    public void deleteMeeting(Long meetingId) throws ValidationException {
        meetingDeleteValidation.isValid(meetingId);

        meetingRepository.deleteMeetingFromMeetingsUsers(meetingId);

        meetingRepository.deleteMeetingFromMeetingsBeverages(meetingId);

        meetingRepository.deleteById(meetingId);
    }

    public MeetingDto addUser(Long meetingId, Long userId) {
        Meeting currentMeeting = getMeetingEntityById(meetingId);
        User userToAdd = userService.getUserEntityById(userId);

        currentMeeting.getParticipates().add(userToAdd);
        meetingRepository.save(currentMeeting);

        return meetingConverter.meetingToMeetingDto(currentMeeting);
    }

    public MeetingDto deleteUser(Long meetingId, Long userId) {
        Meeting currentMeeting = getMeetingEntityById(meetingId);
        User userToAdd = userService.getUserEntityById(userId);

        currentMeeting.getParticipates().remove(userToAdd);
        meetingRepository.save(currentMeeting);

        return meetingConverter.meetingToMeetingDto(currentMeeting);
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

    private Meeting getMeetingEntityById(Long meetingId) {
        Optional<Meeting> meetingEntity = meetingRepository.findById(meetingId);
        if (meetingEntity.isEmpty()) {
            throw new IllegalStateException("Meeting whit id: " + meetingId + " - doesn't exist");
        }
        return meetingEntity.get();
    }



}

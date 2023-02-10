package com.ivan.alcomeeting.service.meeting;

import com.ivan.alcomeeting.converter.MeetingConverter;
import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.repository.BeverageRepository;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.service.user.UserReadService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingCreationService {
    private final MeetingRepository meetingRepository;
    private final MeetingConverter meetingConverter;
    private final UserReadService userReadService;
    private final BeverageRepository beverageRepository;

    public MeetingCreationService(MeetingRepository meetingRepository,
                                  MeetingConverter meetingConverter,
                                  UserReadService userReadService,
                                  BeverageRepository beverageRepository) {
        this.meetingRepository = meetingRepository;
        this.meetingConverter = meetingConverter;
        this.userReadService = userReadService;
        this.beverageRepository = beverageRepository;
    }

    public MeetingDto createMeeting(MeetingCreationDto meetingCreationDto, Principal principal) {
        validateIsAllowedForMeeting(principal.getName(), meetingCreationDto.getMeetingOwner());

        List<Beverage> allBeverages = meetingCreationDto.getBeverages().stream()
                .map(beverageRepository::getReferenceById)
                .collect(Collectors.toList());

        User owner = userReadService.getUserByUserName(principal.getName());

        Meeting createMeeting = meetingConverter.meetingCreationDtoToMeeting(
                meetingCreationDto,
                allBeverages,
                owner);

        meetingRepository.save(createMeeting);

        return meetingConverter.meetingToMeetingDto(createMeeting);
    }

    private void validateIsAllowedForMeeting(String loggedUserName, Long meetingOwner){
        Long loggedUserId = userReadService.getUserByUserName(loggedUserName).getId();

        if (!meetingOwner.equals(loggedUserId)){
            throw new ValidationException("Logged user is different from meeting owner");
        }
    }
}

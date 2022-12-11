package com.ivan.alcomeeting.service.view;

import com.ivan.alcomeeting.converter.MeetingConverter;
import com.ivan.alcomeeting.converter.view.MeetingViewConverter;
import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.dto.view.MeetingViewDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.repository.BeverageRepository;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.validation.MeetingCreationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MeetingViewCreationService {

    private final MeetingRepository meetingRepository;
    private final MeetingViewConverter meetingViewConverter;
    private final UserViewService userViewService;
    private final BeverageRepository beverageRepository;
    private final MeetingCreationValidation meetingCreationValidation;


    @Autowired
    public MeetingViewCreationService(MeetingRepository meetingRepository,
                                      MeetingViewConverter meetingViewConverter,
                                      UserViewService userViewService,
                                      BeverageRepository beverageRepository,
                                      MeetingCreationValidation meetingCreationValidation) {
        this.meetingRepository = meetingRepository;
        this.meetingViewConverter = meetingViewConverter;
        this.userViewService = userViewService;
        this.beverageRepository = beverageRepository;
        this.meetingCreationValidation = meetingCreationValidation;

    }



    public MeetingViewDto createMeeting(MeetingCreationDto meetingCreationDto, Principal principal) throws ValidationException {
        meetingCreationValidation.isValid(meetingCreationDto);

        List<Beverage> allBeverages = meetingCreationDto.getBeverages().stream()
                .map(beverageRepository::getReferenceById)
                .collect(Collectors.toList());

        User owner = userViewService.getUserByUserName(principal.getName());

        Meeting createMeeting = meetingViewConverter.meetingCreationDtoToMeeting(
                meetingCreationDto,
                allBeverages,
                owner);

        meetingRepository.save(createMeeting);

        return meetingViewConverter.meetingToMeetingViewDto(createMeeting, isOwner(principal, createMeeting));

    }


    private boolean isOwner(Principal principal, Meeting meeting) {
        return Objects.equals(meeting.getMeetingOwner().getUserName(), principal.getName());
    }
}

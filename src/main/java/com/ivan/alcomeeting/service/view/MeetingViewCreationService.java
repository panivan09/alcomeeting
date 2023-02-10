package com.ivan.alcomeeting.service.view;

import com.ivan.alcomeeting.converter.view.MeetingViewConverter;
import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.dto.view.MeetingViewDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.repository.BeverageRepository;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.validation.view.MeetingCreationValidator;
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
    private final MeetingCreationValidator meetingCreationValidator;

    @Autowired
    public MeetingViewCreationService(MeetingRepository meetingRepository,
                                      MeetingViewConverter meetingViewConverter,
                                      UserViewService userViewService,
                                      BeverageRepository beverageRepository,
                                      MeetingCreationValidator meetingCreationValidator) {
        this.meetingRepository = meetingRepository;
        this.meetingViewConverter = meetingViewConverter;
        this.userViewService = userViewService;
        this.beverageRepository = beverageRepository;
        this.meetingCreationValidator = meetingCreationValidator;
    }

    public MeetingViewDto createMeeting(MeetingCreationDto meetingCreationDto, Principal principal) throws ValidationException {
        meetingCreationValidator.validate(meetingCreationDto);

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

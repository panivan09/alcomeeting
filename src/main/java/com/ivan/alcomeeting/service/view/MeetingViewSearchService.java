package com.ivan.alcomeeting.service.view;

import com.ivan.alcomeeting.converter.view.MeetingViewConverter;
import com.ivan.alcomeeting.dto.view.MeetingViewDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.service.BeverageService;
import com.ivan.alcomeeting.validation.view.MeetingSearchValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MeetingViewSearchService {

    private final MeetingRepository meetingRepository;
    private final BeverageService beverageService;
    private final MeetingViewConverter meetingViewConverter;
    private final MeetingSearchValidator meetingSearchValidator;

    @Autowired
    public MeetingViewSearchService(MeetingRepository meetingRepository,
                                    BeverageService beverageService,
                                    MeetingViewConverter meetingViewConverter,
                                    MeetingSearchValidator meetingSearchValidator) {
        this.meetingRepository = meetingRepository;
        this.beverageService = beverageService;
        this.meetingViewConverter = meetingViewConverter;
        this.meetingSearchValidator = meetingSearchValidator;
    }

    public List<MeetingViewDto> getMeetingsByBeverageName(String beverageName, Principal principal) throws ValidationException {
        meetingSearchValidator.validate(beverageName);

        Beverage beverage = beverageService.getBeverageEntityByName(beverageName);

        return meetingRepository.findAllByBeverage(beverage.getId()).stream()
                .map(meeting -> meetingViewConverter.meetingToMeetingViewDto(meeting, isOwner(principal, meeting)))
                .collect(Collectors.toList());
    }

    public List<MeetingViewDto> getMeetingsByDate(LocalDate date, Principal principal) throws ValidationException {
        meetingSearchValidator.isDateValid(date);

        return meetingRepository.findAllByDateBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
        ).stream()
                .map(meeting -> meetingViewConverter.meetingToMeetingViewDto(meeting, isOwner(principal, meeting)))
                .collect(Collectors.toList());
    }

    public List<MeetingViewDto> getMeetingsByAddress(String address, Principal principal) throws ValidationException {
        meetingSearchValidator.isAddressValid(address);

        return meetingRepository.findAllByAddress(address).stream()
                .map(meeting -> meetingViewConverter.meetingToMeetingViewDto(meeting, isOwner(principal, meeting)))
                .collect(Collectors.toList());
    }

    private boolean isOwner(Principal principal, Meeting meeting) {
        return Objects.equals(meeting.getMeetingOwner().getUserName(), principal.getName());
    }
}

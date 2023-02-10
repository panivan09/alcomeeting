package com.ivan.alcomeeting.service.meeting;

import com.ivan.alcomeeting.converter.MeetingConverter;
import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.repository.BeverageRepository;
import com.ivan.alcomeeting.repository.MeetingRepository;
import com.ivan.alcomeeting.service.user.UserReadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class MeetingCreationServiceTest {
    @Mock
    private MeetingRepository meetingRepository;
    @Mock
    private MeetingConverter meetingConverter;
    @Mock
    private UserReadService userReadService;
    @Mock
    private BeverageRepository beverageRepository;
    @Mock
    private Principal principal;

    @InjectMocks
    private MeetingCreationService meetingCreationService;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMeeting_throwValidationExceptionWhenUserDoesNotHaveAccess() {
        // Given
        Long loggedUserId = 1L;
        Long meetingOwnerId = 3L;
        String loggedUserName = "Poc";
        String exceptionMessage = "Logged user is different from meeting owner";

        User loggedUser = new User();
        loggedUser.setId(loggedUserId);

        MeetingCreationDto meetingCreationDto = MeetingCreationDto.builder()
                .meetingOwner(meetingOwnerId)
                .build();


        when(principal.getName()).thenReturn(loggedUserName);
        when(userReadService.getUserByUserName(loggedUserName)).thenReturn(loggedUser);

        // When and Than
        assertThatThrownBy(()-> meetingCreationService.createMeeting(meetingCreationDto, principal))
                .isInstanceOf(ValidationException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void createMeeting_returnMeetingDtoAfterCreation() {
        // Given
        Long loggedUserId = 3L;
        Long meetingOwnerId = 3L;
        String loggedUserName = "Poc";
        Long beverageId1 = 1L;
        Long beverageId2 = 2L;

        Beverage beverage1 = new Beverage(beverageId1, "Any", "Any");
        Beverage beverage2 = new Beverage(beverageId2, "Any", "Any");
        BeverageDto beverageDto1 = new BeverageDto(beverageId1, "Any", "Any");
        BeverageDto beverageDto2 = new BeverageDto(beverageId2, "Any", "Any");

        List<Beverage> allBeverage = List.of(beverage1, beverage2);

        User user = new User();
        user.setId(loggedUserId);

        UserDto userDto = new UserDto();
        userDto.setId(loggedUserId);

        MeetingCreationDto meetingCreationDto = MeetingCreationDto.builder()
                .meetingOwner(meetingOwnerId)
                .beverages(Set.of(beverageId1, beverageId2))
                .build();

        Meeting meeting = new Meeting(5L,
                "Any",
                null,
                "Any",
                user,
                null,
                Set.copyOf(allBeverage));

        MeetingDto expectedMeeting = new MeetingDto(5L,
                "Any",
                null,
                "Any",
                userDto,
                null,
                List.of(beverageDto1, beverageDto2));

        when(principal.getName()).thenReturn(loggedUserName);
        when(beverageRepository.getReferenceById(beverageId1)).thenReturn(beverage1);
        when(beverageRepository.getReferenceById(beverageId2)).thenReturn(beverage2);
        when(userReadService.getUserByUserName(loggedUserName)).thenReturn(user);
        when(meetingConverter.meetingCreationDtoToMeeting(meetingCreationDto, allBeverage, user)).thenReturn(meeting);
        when(meetingConverter.meetingToMeetingDto(meeting)).thenReturn(expectedMeeting);

        // When
        MeetingDto actual = meetingCreationService.createMeeting(meetingCreationDto, principal);

        // Than
        verify(meetingRepository, times(1)).save(meeting);
        assertThat(actual).isEqualTo(expectedMeeting);
    }
}
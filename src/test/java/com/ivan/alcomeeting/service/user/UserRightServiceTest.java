package com.ivan.alcomeeting.service.user;

import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.entity.Meeting;
import com.ivan.alcomeeting.entity.Permission;
import com.ivan.alcomeeting.entity.Role;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.EntityNotFoundException;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.service.meeting.MeetingReadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


class UserRightServiceTest {

    @Mock
    private UserReadService userReadService;
    @Mock
    private MeetingReadService meetingReadService;
    @Mock
    private Principal principal;

    @InjectMocks
    private UserRightService userRightService;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateIsAllowedForMeeting_whenUserIsAllowed() {
        //Given
        Long meetingId = 1L;
        Long userId = 3L;
        String userName = "UserName";
        Set<Role> roles = Set.of(new Role(1L, "Any", "low access", Set.of(new Permission())));

        User loggedUser = new User(userId,
                "Any",
                "Any",
                "Any",
                userName,
                "Any",
                null,
                null,
                null,
                roles);

        UserDto userDto = new UserDto(userId,
                "Any",
                "Any",
                "Any",
                userName,
                "Any",
                null);

        MeetingDto meetingDto = new MeetingDto(meetingId,
                "Any",
                "Any",
                "Any",
                userDto,
                null,
                null);

        when(principal.getName()).thenReturn(userName);
        when(userReadService.getUserByUserName(userName)).thenReturn(loggedUser);
        when(meetingReadService.getMeetingById(meetingId)).thenReturn(meetingDto);

        //When and Then
        assertThatCode(() -> userRightService.validateIsAllowedForMeeting(principal, meetingId))
                .doesNotThrowAnyException();
    }

    @Test
    void validateIsAllowedForMeeting_whenAdminIsAllowed() {
        //Given
        Long meetingId = 1L;
        Long userLoggedId = 2L;
        Long userId = 3L;
        String userName = "UserName";
        String roleName = "ADMIN";

        Role role = new Role(1L, roleName, "low access", Set.of(new Permission()));
        Set<Role> roles = Set.of(role);

        User loggedUser = new User(userLoggedId,
                "Any",
                "Any",
                "Any",
                userName,
                "Any",
                new Meeting(),
                null,
                null,
                roles);

        UserDto userDto = new UserDto(userId,
                "Any",
                "Any",
                "Any",
                userName,
                "Any",
                null);

        MeetingDto meetingDto = new MeetingDto(meetingId,
                "Any",
                "Any",
                "Any",
                userDto,
                null,
                null);

        when(principal.getName()).thenReturn(userName);
        when(userReadService.getUserByUserName(userName)).thenReturn(loggedUser);
        when(meetingReadService.getMeetingById(meetingId)).thenReturn(meetingDto);

        //When and Then
        assertThatCode(() -> userRightService.validateIsAllowedForMeeting(principal, meetingId))
                .doesNotThrowAnyException();
    }

    @Test
    void validateIsAllowedForMeeting_throwValidationExceptionWhenWrongUserAndNotAdmin() {
        //Given
        Long meetingId = 1L;
        Long userLoggedId = 2L;
        Long userId = 3L;
        String userName = "UserName";
        String userRoleName = "POC";
        String exceptionMessage = "Current user does not have access";

        Set<Role> roles = Set.of(new Role(1L, userRoleName, "low access", Set.of(new Permission())));

        User loggedUser = new User(userLoggedId,
                "Any",
                "Any",
                "Any",
                userName,
                "Any",
                new Meeting(),
                null,
                null,
                roles);

        UserDto userDto = new UserDto(userId,
                "Any",
                "Any",
                "Any",
                userName,
                "Any",
                null);

        MeetingDto meetingDto = new MeetingDto(meetingId,
                "Any",
                "Any",
                "Any",
                userDto,
                null,
                null);

        when(principal.getName()).thenReturn(userName);
        when(userReadService.getUserByUserName(userName)).thenReturn(loggedUser);
        when(meetingReadService.getMeetingById(meetingId)).thenReturn(meetingDto);

        //When and Then
        assertThatThrownBy(()-> userRightService.validateIsAllowedForMeeting(principal, meetingId))
                .isInstanceOf(ValidationException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void validateIsAllowedForMeeting_throwValidationExceptionWhenUserRepositoryHasNoUser() {
        //Given
        Long meetingId = 1L;
        String userName = "UserName";
        String exceptionMessage = "Can't find user or meeting for right validation";

        when(principal.getName()).thenReturn(userName);
        when(userReadService.getUserByUserName(userName)).thenThrow(new EntityNotFoundException(exceptionMessage));

        //When and Then
        assertThatThrownBy(()-> userRightService.validateIsAllowedForMeeting(principal, meetingId))
                .isInstanceOf(ValidationException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void validateIsAllowedForMeeting_throwValidationExceptionWhenMeetingRepositoryHasNoMeeting() {
        //Given
        Long meetingId = 1L;
        Long userId = 3L;
        String userName = "UserName";
        String exceptionMessage = "Can't find user or meeting for right validation";

        Set<Role> roles = Set.of(new Role(1L, "Any", "low access", Set.of(new Permission())));

        User loggedUser = new User(userId,
                "Any",
                "Any",
                "Any",
                userName,
                "Any",
                new Meeting(),
                null,
                null,
                roles);

        when(principal.getName()).thenReturn(userName);
        when(userReadService.getUserByUserName(userName)).thenReturn(loggedUser);
        when(meetingReadService.getMeetingById(meetingId)).thenThrow(new EntityNotFoundException(exceptionMessage));

        //When and Then
        assertThatThrownBy(()-> userRightService.validateIsAllowedForMeeting(principal, meetingId))
                .isInstanceOf(ValidationException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void validateIsAllowedForUser_whenUserIsAllowed() {
        //Given
        Long userId = 3L;
        Long userLoggedId = 3L;
        String userName = "UserNam";

        User userEntity = new User(userLoggedId,
                "Any",
                "Any",
                "Any",
                userName,
                "Any",
                new Meeting(),
                null,
                null);

        when(principal.getName()).thenReturn(userName);
        when(userReadService.getUserByUserName(userName)).thenReturn(userEntity);

        //When and Then
        assertThatCode(() -> userRightService.validateIsAllowedForUser(principal, userId))
                .doesNotThrowAnyException();
    }

    @Test
    void validateIsAllowedForUser_throwValidationExceptionWhenWrongUser() {
        //Given
        Long userId = 3L;
        Long userLoggedId = 1L;
        String userName = "UserNam";
        String exceptionMessage = "Current user does not have access";

        User userEntity = new User(userLoggedId,
                "Any",
                "Any",
                "Any",
                userName,
                "Any",
                new Meeting(),
                null,
                null);

        when(principal.getName()).thenReturn(userName);
        when(userReadService.getUserByUserName(userName)).thenReturn(userEntity);

        //When and Then
        assertThatThrownBy(() -> userRightService.validateIsAllowedForUser(principal, userId))
                .isInstanceOf(ValidationException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void validateIsAllowedForUser_throwEntityNotFoundExceptionWhenUserRepositoryHasNoUser() {
        //Given
        Long userId = 3L;
        String userName = "UserNam";
        String exceptionMessage = "This user doesn't exist by name: " + userName;

        when(principal.getName()).thenReturn(userName);
        when(userReadService.getUserByUserName(userName)).thenThrow(new EntityNotFoundException(exceptionMessage));

        //When and Then
        assertThatThrownBy(() -> userRightService.validateIsAllowedForUser(principal, userId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(exceptionMessage);
    }
}
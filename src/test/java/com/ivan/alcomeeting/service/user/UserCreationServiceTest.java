package com.ivan.alcomeeting.service.user;

import com.ivan.alcomeeting.converter.UserConverter;
import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Permission;
import com.ivan.alcomeeting.entity.Role;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.repository.BeverageRepository;
import com.ivan.alcomeeting.repository.RoleRepository;
import com.ivan.alcomeeting.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserCreationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverter userConverter;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BeverageRepository beverageRepository;
    @Mock
    private UserSecurityService userSecurityService;

    @InjectMocks
    private UserCreationService userCreationService;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void userCreate_throwValidationExceptionIfUserEmailIsOccupied(){
        //Given
        String email = "occupied@email.com";
        String expectedMessage = "Email is occupied";
        UserCreationDto userCreationDto = new UserCreationDto(1L,
                                                                "Poc",
                                                                "Pocavich",
                                                                email,
                                                                "ppp",
                                                                "p1",
                                                                Set.of(1L, 3L));
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(new User()));

//        Using "assert" from JUnit 5:
//        Exception exception = assertThrows(ValidationException.class, ()-> {userCreationService.createUser(userCreationDto);});
//        String expectedMessage = "Email is occupied";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));

//       When and Then
//        Using "assertj" library:
        assertThatThrownBy(()->{
            userCreationService.createUser(userCreationDto);
        }).isInstanceOf(ValidationException.class)
                .hasMessage(expectedMessage);

    }

    @Test
    public void userCreate_throwValidationExceptionIfUserNameIsOccupied(){
//        Given
        String userName = "UserName";
        String expectedMessage = "User name is occupied";
        UserCreationDto userCreationDto = new UserCreationDto(1L,
                                                                "Poc",
                                                                "Pocavich",
                                                                "email",
                                                                userName,
                                                                "p1",
                                                                Set.of(1L, 3L));
//        When
        when(userRepository.findUserByUserName(userName)).thenReturn(Optional.of(new User()));

//        Then
        assertThatThrownBy(()->{
            userCreationService.createUser(userCreationDto);
        }).isInstanceOf(ValidationException.class)
                .hasMessage(expectedMessage);

    }

    @Test
    public void userCreate_returnSavedUserDto(){
//      Given
        Long beverageId = 1L;
        Beverage beverage = new Beverage(beverageId, "Jin", "London");
        List<Beverage> beveragesEntity = List.of(beverage);

        Long roleId = 1L;
        Role userRole = new Role(1L, "USER", "low access", Set.of(new Permission()));
        Set<Role> rolesEntity = Set.of(userRole);

        String password = "password";
        String encodePassword = "p123";

        UserDto userExpectedDto = UserDto.builder()
                .id(1L)
                .build();

        User userToCreateEntity = new User(1L,
                "Poc",
                "Pocanovich",
                "email",
                "userName",
                encodePassword,
                null,
                Set.of(beverage),
                null);

        UserCreationDto userCreationDto = new UserCreationDto(1L,
                "Poc",
                "Pocanovich",
                "email",
                "userName",
                password,
                Set.of(beverageId));

        when(beverageRepository.getReferenceById(beverageId)).thenReturn(beverage);
        when(roleRepository.getReferenceById(roleId)).thenReturn(userRole);
        when(userSecurityService.encodePassword(password)).thenReturn(encodePassword);
        when(userConverter.userCreationDtoToUser(userCreationDto, beveragesEntity, rolesEntity)).thenReturn(userToCreateEntity);
        when(userConverter.userToUserDto(userToCreateEntity)).thenReturn(userExpectedDto);

        // When
        UserDto actualUserDto = userCreationService.createUser(userCreationDto);

        // Then
        assertThat(actualUserDto).isEqualTo(userExpectedDto);
        verify(userRepository, times(1)).save(userToCreateEntity);// check that method was call

    }

}
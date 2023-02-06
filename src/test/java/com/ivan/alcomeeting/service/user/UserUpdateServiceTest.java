package com.ivan.alcomeeting.service.user;

import com.ivan.alcomeeting.converter.UserConverter;
import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.dto.UserFullNameDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.EntityNotFoundException;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.repository.UserRepository;
import com.ivan.alcomeeting.service.BeverageService;
import com.ivan.alcomeeting.validation.UserPasswordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserUpdateServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverter userConverter;
    @Mock
    private BeverageService beverageService;
    @Mock
    private UserSecurityService userSecurityService;
    @Mock
    private UserReadService userReadService;
    @Mock
    private UserPasswordValidator userPasswordValidator;

    @InjectMocks
    private UserUpdateService userUpdateService;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUserFullName_returnUserDtoAfterUpdate() {
        //Given
        Long userId = 3L;
        String name = "Poc";
        String lastName = "LastPoc";
        UserFullNameDto userFullNameDto = new UserFullNameDto(name, lastName);

        User userEntity = new User(userId,
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                null,
                null,
                null);

        UserDto expectedUserDto = new UserDto(userId,
                name,
                lastName,
                "Any",
                "Any",
                "Any",
                null);

        when(userReadService.getUserEntityById(userId)).thenReturn(userEntity);
        when(userConverter.userToUserDto(userEntity)).thenReturn(expectedUserDto);

        //When
        UserDto actualUserDto = userUpdateService.updateUserFullName(userId, userFullNameDto);

        //Then
        verify(userRepository, times(1)).save(userEntity);
        assertThat(actualUserDto).isEqualTo(expectedUserDto);
    }

    @Test
    void updateUserFullName_updatesOnlyNameIfNewLastNameIsNull() {
        //Given
        Long userId = 3L;
        String name = "Poc";
        UserFullNameDto userFullNameDto = new UserFullNameDto(name, null);

        User userEntity = new User(userId,
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                null,
                null,
                null);

        UserDto expectedUserDto = new UserDto(userId,
                name,
                "Any",
                "Any",
                "Any",
                "Any",
                null);

        when(userReadService.getUserEntityById(userId)).thenReturn(userEntity);
        when(userConverter.userToUserDto(userEntity)).thenReturn(expectedUserDto);

        //When
        UserDto actualUserDto = userUpdateService.updateUserFullName(userId, userFullNameDto);

        //Then
        verify(userRepository, times(1)).save(userEntity);
        assertThat(actualUserDto).isEqualTo(expectedUserDto);
    }

    @Test
    void updateUserFullName_updatesOnlyLastNameIfNewNameIsNull() {
        //Given
        Long userId = 3L;
        String lastName = "Poc";
        UserFullNameDto userFullNameDto = new UserFullNameDto(null, lastName);

        User userEntity = new User(userId,
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                null,
                null,
                null);

        UserDto expectedUserDto = new UserDto(userId,
                "Any",
                lastName,
                "Any",
                "Any",
                "Any",
                null);

        when(userReadService.getUserEntityById(userId)).thenReturn(userEntity);
        when(userConverter.userToUserDto(userEntity)).thenReturn(expectedUserDto);

        //When
        UserDto actualUserDto = userUpdateService.updateUserFullName(userId, userFullNameDto);

        //Then
        verify(userRepository, times(1)).save(userEntity);
        assertThat(actualUserDto).isEqualTo(expectedUserDto);
    }

    @Test
    void updateUserFullName_throwEntityNotFoundException() {
        //Given
        Long userId = 3L;
        String exceptionMessage = "This user doesn't exist by Id: " + userId;
        UserFullNameDto userFullNameDto = new UserFullNameDto("Any", "Any");

        when(userReadService.getUserEntityById(userId)).thenThrow(new EntityNotFoundException(exceptionMessage));

        //When and Then
        assertThatThrownBy(()-> userUpdateService.updateUserFullName(userId, userFullNameDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void updateUserPassword_throwValidationExceptionIfValidatorFailsCheck() {
        //Given
        Long userId = 3L;
        String password = "";
        String massage = "User password should not be null or empty";

        doThrow(new ValidationException(massage)).when(userPasswordValidator).validate(password);

        // When and Then
        assertThatThrownBy(()-> userUpdateService.updateUserPassword(userId, password))
                .isInstanceOf(ValidationException.class)
                .hasMessage(massage);
    }

    @Test
    void updateUserPassword_throwEntityNotFoundExceptionIfUserRepositoryHasNoUser() {
        //Given
        Long userId = 3L;
        String password = "password";
        String exceptionMessage = "This user doesn't exist by Id: " + userId;

        when(userReadService.getUserEntityById(userId)).thenThrow(new EntityNotFoundException(exceptionMessage));

        //When and Then
        assertThatThrownBy(()->userUpdateService.updateUserPassword(userId, password))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void updateUserPassword_returnUserDtoWithUpdatedPassword() {
        //Given
        Long userId = 3L;
        String password = "password";
        String encodePassword = "Pp1";

        User user = new User();
        user.setId(userId);
        user.setPassword("lalala");

        UserDto expectedUser = new UserDto();
        expectedUser.setId(userId);
        expectedUser.setPassword(encodePassword);

        when(userReadService.getUserEntityById(userId)).thenReturn(user);
        when(userSecurityService.encodePassword(password)).thenReturn(encodePassword);
        when(userConverter.userToUserDto(user)).thenReturn(expectedUser);

        //When
        UserDto actualUserDto = userUpdateService.updateUserPassword(userId, password);

        //Then
        verify(userRepository, times(1)).save(user);
        assertThat(actualUserDto).isEqualTo(expectedUser);
    }

    @Test
    void updateUserEmail_throwEntityNotFoundExceptionIfUserRepositoryHasNoUser() {
        //Given
        Long userId = 3L;
        String email = "email@gmail.com";
        String exceptionMessage = "This user doesn't exist by Id: " + userId;

        when(userReadService.getUserEntityById(userId)).thenThrow(new EntityNotFoundException(exceptionMessage));

        //When and Then
        assertThatThrownBy(()->userUpdateService.updateUserEmail(userId, email))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void updateUserEmail_returnUserDtoWithUpdatedEmail() {
        //Given
        Long userId = 3L;
        String email = "email@gmail.com";

        User user = new User();
        user.setId(userId);
        user.setEmail("lala@gmail.com");

        UserDto expectedUser = new UserDto();
        expectedUser.setId(userId);
        expectedUser.setEmail(email);

        when(userReadService.getUserEntityById(userId)).thenReturn(user);
        when(userConverter.userToUserDto(user)).thenReturn(expectedUser);

        //When
        UserDto actualUserDto = userUpdateService.updateUserEmail(userId, email);

        //Then
        verify(userRepository, times(1)).save(user);
        assertThat(actualUserDto).isEqualTo(expectedUser);
    }

    @Test
    void addBeverage_throwEntityNotFoundExceptionIfUserRepositoryHasNoUser() {
        //Given
        Long userId = 3L;
        Long beverageId = 1L;
        String exceptionMessage = "This user doesn't exist by Id: " + userId;

        when(userReadService.getUserEntityById(userId)).thenThrow(new EntityNotFoundException(exceptionMessage));

        //When and Then
        assertThatThrownBy(()->userUpdateService.addBeverage(userId, beverageId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void addBeverage_throwEntityNotFoundExceptionIfBeverageRepositoryHasNoBeverage() {
        //Given
        Long userId = 3L;
        Long beverageId = 1L;
        String exceptionMessage = "The beverage does not exist by Id: " + beverageId;

        when(userReadService.getUserEntityById(userId)).thenReturn(new User());
        when(beverageService.getBeverageEntityById(beverageId)).thenThrow(new EntityNotFoundException(exceptionMessage));

        //When and Then
        assertThatThrownBy(()->userUpdateService.addBeverage(userId, beverageId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void addBeverage_returnUserDtoWithAddedBeverage() {
        //Given
        Long userId = 3L;
        Long beverageId = 1L;

        Beverage beverageToAdd = new Beverage();
        beverageToAdd.setId(beverageId);
        beverageToAdd.setName("Shmurdyak");

        BeverageDto beverageDto = new BeverageDto();
        beverageDto.setId(beverageId);
        beverageDto.setName("Shmurdyak");

        User user = new User();
        user.setId(userId);
        user.setBeverages(new HashSet<>());

        UserDto expectedUser = new UserDto();
        expectedUser.setId(userId);
        expectedUser.setBeverages(List.of(beverageDto));

        when(userReadService.getUserEntityById(userId)).thenReturn(user);
        when(beverageService.getBeverageEntityById(beverageId)).thenReturn(beverageToAdd);
        when(userConverter.userToUserDto(user)).thenReturn(expectedUser);

        //When
        UserDto actualUserDto = userUpdateService.addBeverage(userId, beverageId);

        //Then
        verify(userRepository, times(1)).save(user);
        assertThat(actualUserDto).isEqualTo(expectedUser);
    }

    @Test
    void deleteBeverage_throwEntityNotFoundExceptionIfUserRepositoryHasNoUser() {
        //Given
        Long userId = 3L;
        Long beverageId = 1L;
        String exceptionMessage = "This user doesn't exist by Id: " + userId;

        when(userReadService.getUserEntityById(userId)).thenThrow(new EntityNotFoundException(exceptionMessage));

        //When and Then
        assertThatThrownBy(()->userUpdateService.deleteBeverage(userId, beverageId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void deleteBeverage_throwEntityNotFoundExceptionIfBeverageRepositoryHasNoBeverage() {
        //Given
        Long userId = 3L;
        Long beverageId = 1L;
        String exceptionMessage = "The beverage does not exist by Id: " + beverageId;

        when(userReadService.getUserEntityById(userId)).thenReturn(new User());
        when(beverageService.getBeverageEntityById(beverageId)).thenThrow(new EntityNotFoundException(exceptionMessage));

        //When and Then
        assertThatThrownBy(()->userUpdateService.deleteBeverage(userId, beverageId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void deleteBeverage_returnUserDtoAfterDeleteBeverage() {
        //Given
        Long userId = 3L;
        Long beverageId = 1L;

        Beverage beverageToDelete = new Beverage();
        beverageToDelete.setId(beverageId);
        beverageToDelete.setName("Shmurdyak");

        Set<Beverage> userBeverages = new HashSet<>();
        userBeverages.add(beverageToDelete);

        User user = new User();
        user.setId(userId);
        user.setBeverages(userBeverages);

        UserDto expectedUser = new UserDto();
        expectedUser.setId(userId);
        expectedUser.setBeverages(List.of());

        when(userReadService.getUserEntityById(userId)).thenReturn(user);
        when(beverageService.getBeverageEntityById(beverageId)).thenReturn(beverageToDelete);
        when(userConverter.userToUserDto(user)).thenReturn(expectedUser);

        //When
        UserDto actualUserDto = userUpdateService.addBeverage(userId, beverageId);

        //Then
        verify(userRepository, times(1)).save(user);
        assertThat(actualUserDto).isEqualTo(expectedUser);
    }
}
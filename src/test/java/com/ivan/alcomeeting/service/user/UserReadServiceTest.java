package com.ivan.alcomeeting.service.user;

import com.ivan.alcomeeting.converter.UserConverter;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.EntityNotFoundException;
import com.ivan.alcomeeting.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class UserReadServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserReadService userReadService;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void getAllUsers_returnsAllUsersDto() {
        //Given
        User userOne = new User(1L,
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                null,
                Set.of(),
                Set.of());
        User userTwo = new User(2L,
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                null,
                Set.of(),
                Set.of());

        UserDto userDtoOne = new UserDto(1L,"Any","Any","Any","Any","Any", List.of());
        UserDto userDtoTwo = new UserDto(2L,"Any","Any","Any","Any","Any", List.of());

        List<UserDto> expected = List.of(userDtoOne, userDtoTwo);

        when(userConverter.userToUserDto(userOne)).thenReturn(userDtoOne);
        when(userConverter.userToUserDto(userTwo)).thenReturn(userDtoTwo);
        when(userRepository.findAll()).thenReturn(List.of(userOne, userTwo));//???????????????????????????????????????????????

        //When
        List<UserDto> actual = userReadService.getAllUsers();

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getUserById_returnUserDtoIfRepositoryHasUser() {
        //Given
        Long userId = 3L;
        User user = new User(userId,
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
                "Any",
                "Any",
                "Any",
                "Any",
                null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userConverter.userToUserDto(user)).thenReturn(expectedUserDto);

        //When
        UserDto actualUser = userReadService.getUserById(userId);

        //Then
        assertThat(actualUser).isEqualTo(expectedUserDto);
    }

    @Test
    public void getUserById_returnEntityNotFoundExceptionIfRepositoryHasNoUser() {
        //Given
        Long userId = 3L;
        String message = "This user doesn't exist by Id: " + userId;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //When and Then
        assertThatThrownBy(()->{
            userReadService.getUserById(userId);
        }).isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);
    }

    @Test
    void getUserEntitiesByIds_returnAllUsersEntity() {
        //Given
        Long userOneId = 1L;
        Long userTwoId = 2L;

        User userOne = new User(userOneId,
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                null,
                Set.of(),
                Set.of());
        User userTwo = new User(userTwoId,
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                null,
                Set.of(),
                Set.of());

        List<Long> usersId = List.of(userOneId, userTwoId);
        List<User> expectedUsers = List.of(userOne, userTwo);

        when(userRepository.findAllById(usersId)).thenReturn(expectedUsers);//???????????????????????????????????????????????

        //When
        List<User> actualUsers = userReadService.getUserEntitiesByIds(usersId);

        //Then
        assertThat(actualUsers).isEqualTo(expectedUsers);
    }

    @Test
    public void getUserByUserName_returnUserEntityIfRepositoryHasUser() {
        //Given
        String userName = "UserName";
        User expectedUser = new User(3L,
                "Any",
                "Any",
                "Any",
                userName,
                "Any",
                null,
                null,
                null);

        when(userRepository.findUserByUserName(userName)).thenReturn(Optional.of(expectedUser));

        //When
        User actualUser = userReadService.getUserByUserName(userName);

        //Then
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    public void getUserByUserName_returnEntityNotFoundExceptionIfRepositoryHasNoUser() {
        //Given
        String userName = "UserName";
        String message = "This user doesn't exist by name: " + userName;

        when(userRepository.findUserByUserName(userName)).thenReturn(Optional.empty());

        //When and Then
        assertThatThrownBy(()->{
            userReadService.getUserByUserName(userName);
        }).isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);
    }

    @Test
    void getUserEntityById_returnUserEntityIfRepositoryHasUser() {
        //Given
        Long userId = 3L;
        User expectedUser = new User(userId,
                "Any",
                "Any",
                "Any",
                "Any",
                "Any",
                null,
                null,
                null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        //When
        User actualUser = userReadService.getUserEntityById(userId);

        //Then
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    public void getUserEntityById_returnEntityNotFoundExceptionIfRepositoryHasNoUser() {
        //Given
        Long userId = 3L;
        String message = "This user doesn't exist by Id: " + userId;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //When and Then
        assertThatThrownBy(()->{
            userReadService.getUserEntityById(userId);
        }).isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);
    }
}
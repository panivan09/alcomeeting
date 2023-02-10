package com.ivan.alcomeeting.service.user;

import com.ivan.alcomeeting.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserDeleteServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDeleteService userDeleteService;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deleteUser_checkThatUserWasDelete(){
        //Given
        Long userId = 3L;

        //When
        userDeleteService.deleteUser(userId);

        //Then
        verify(userRepository, times(1)).deleteUserFromMeetingsUsers(userId);
        verify(userRepository, times(1)).deleteUserFromUsersBeverages(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }
}
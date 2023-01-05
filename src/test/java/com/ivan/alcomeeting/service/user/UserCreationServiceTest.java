package com.ivan.alcomeeting.service.user;

import com.ivan.alcomeeting.converter.UserConverter;
import com.ivan.alcomeeting.dto.UserCreationDto;
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

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


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
    public void userCreate_throwValidationExceptionIfUserEmailOccupied(){

        String email = "occupied@email.com";
        UserCreationDto userCreationDto = new UserCreationDto(1L,
                                                                "Poc",
                                                                "Pocavich",
                email,
                                                                "ppp",
                                                                "p1",
                                                                Set.of(1L, 3L));

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(new User()));

        Exception exception = assertThrows(ValidationException.class, ()-> {userCreationService.createUser(userCreationDto);});
        String expectedMessage = "Email is occupied";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));



//        doThrow(new ValidationException()).when(userCreationService.createUser(userCreationDto));
//        assertThrows(ValidationException.class, ()-> {userCreationService.createUser(userCreationDto);});

    }
}
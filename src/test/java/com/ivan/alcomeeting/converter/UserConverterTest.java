package com.ivan.alcomeeting.converter;

import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.dto.view.UserViewDto;
import com.ivan.alcomeeting.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserConverterTest {
    @Mock
    private BeverageConverter beverageConverter;
    @InjectMocks
    private UserConverter userConverter;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void userToUserDto_returnUserDtoAfterConversion() {
        // Given
        Long userId = 1L;
        String name = "Poc";
        String lastName = "Local";
        String email = "poc@gmail.com";
        String userName = "localpoc";
        String password = "Password123";

        Beverage beverage1 = new Beverage(1L,"Gin","London");
        Beverage beverage2 = new Beverage(2L,"Whiskey","Irish");

        BeverageDto beverageDto1 = new BeverageDto(1L,"Gin","London");
        BeverageDto beverageDto2 = new BeverageDto(2L,"Whiskey","Irish");

        User user = new User(userId,
                name,
                lastName,
                email,
                userName,
                password,
                null,
                Set.of(beverage1, beverage2),
                null);

        UserDto expected = new UserDto(userId,
                name,
                lastName,
                email,
                userName,
                password,
                List.of(beverageDto1, beverageDto2));

        when(beverageConverter.beverageToBeverageDto(beverage1)).thenReturn(beverageDto1);
        when(beverageConverter.beverageToBeverageDto(beverage2)).thenReturn(beverageDto2);

        // When
        UserDto actual = userConverter.userToUserDto(user);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void userCreationDtoToUser_returnUserAfterConversion() {
        // Given
        Long beverage1Id = 1L;
        Long beverage2Id = 2L;
        String name = "Poc";
        String lastName = "Local";
        String email = "poc@gmail.com";
        String userName = "localpoc";
        String password = "Password123";

        Beverage beverage1 = new Beverage(beverage1Id,"Gin","London");
        Beverage beverage2 = new Beverage(beverage2Id,"Whiskey","Irish");
        List<Beverage> beverages = List.of(beverage1, beverage2);

        Role userRole = new Role(1L, "USER", "Low access", Set.of(new Permission()));
        Set<Role> rolesEntity = Set.of(userRole);

        UserCreationDto userCreationDto = new UserCreationDto();
        userCreationDto.setName(name);
        userCreationDto.setLastName(lastName);
        userCreationDto.setEmail(email);
        userCreationDto.setUserName(userName);
        userCreationDto.setPassword(password);
        userCreationDto.setBeverages(Set.of(beverage1Id, beverage2Id));

        User expected = new User();
        expected.setName(name);
        expected.setLastName(lastName);
        expected.setEmail(email);
        expected.setUserName(userName);
        expected.setPassword(password);
        expected.setBeverages(Set.of(beverage1, beverage2));
        expected.setRoles(rolesEntity);

        // When
        User actual = userConverter.userCreationDtoToUser(userCreationDto, beverages, rolesEntity);

        // Then
        //TODO: is it right approach?
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void userDtoToUser_returnUserAfterConversion() {
        // Given
        String name = "Poc";
        String lastName = "Local";
        String email = "poc@gmail.com";
        String userName = "localpoc";
        String password = "Password123";

        Beverage beverage1 = new Beverage(1L,"Gin","London");
        Beverage beverage2 = new Beverage(2L,"Whiskey","Irish");

        BeverageDto beverageDto1 = new BeverageDto(1L,"Gin","London");
        BeverageDto beverageDto2 = new BeverageDto(2L,"Whiskey","Irish");

        UserDto userDto = new UserDto(null,
                name,
                lastName,
                email,
                userName,
                password,
                List.of(beverageDto1, beverageDto2));

        User expected = new User(null,
                name,
                lastName,
                email,
                userName,
                password,
                null,
                Set.of(beverage1, beverage2),
                null);

        when(beverageConverter.beverageDtoToBeverage(beverageDto1)).thenReturn(beverage1);
        when(beverageConverter.beverageDtoToBeverage(beverageDto2)).thenReturn(beverage2);

        // When
        User actual = userConverter.userDtoToUser(userDto);

        // Then
        //TODO: is it right approach?
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void userToUserViewDto_returnUserViewDtoAfterConversion() {
        // Given
        Long userId = 3L;
        String name = "Poc";
        String lastName = "Local";
        String email = "poc@gmail.com";
        String userName = "localpoc";
        String password = "Password123";

        String beveragesName = "Gin, Whiskey";
        Beverage beverage1 = new Beverage(1L,"Gin","London");
        Beverage beverage2 = new Beverage(2L,"Whiskey","Irish");

        User user = new User(userId,
                name,
                lastName,
                email,
                userName,
                password,
                null,
                Set.of(beverage1, beverage2),
                null);

        UserViewDto expected = new UserViewDto(userId,
                name,
                lastName,
                email,
                beveragesName);

        // When
        UserViewDto actual = userConverter.userToUserViewDto(user);

        // Then
        //TODO: Should I use "usingRecursiveComparison()" here?
        assertThat(actual).isEqualTo(expected);
    }
}
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
        Beverage beverage1 = new Beverage(1L,"Gin","London");
        Beverage beverage2 = new Beverage(2L,"Whiskey","Irish");

        BeverageDto beverageDto1 = new BeverageDto(1L,"Gin","London");
        BeverageDto beverageDto2 = new BeverageDto(2L,"Whiskey","Irish");

        User user = new User(1L,
                "Poc",
                "Local",
                "poc@gmail.com",
                "localpoc",
                "Password123",
                null,
                Set.of(beverage1, beverage2),
                null);

        UserDto expected = new UserDto(1L,
                "Poc",
                "Local",
                "poc@gmail.com",
                "localpoc",
                "Password123",
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
        Beverage beverage1 = new Beverage(beverage1Id,"Gin","London");
        Beverage beverage2 = new Beverage(beverage2Id,"Whiskey","Irish");
        List<Beverage> beverages = List.of(beverage1, beverage2);

        Role userRole = new Role(1L, "USER", "Low access", Set.of(new Permission()));
        Set<Role> rolesEntity = Set.of(userRole);

        UserCreationDto userCreationDto = new UserCreationDto();
        userCreationDto.setName("Poc");
        userCreationDto.setLastName("Local");
        userCreationDto.setEmail("poc@gmail.com");
        userCreationDto.setUserName("localpoc");
        userCreationDto.setPassword("Password123");
        userCreationDto.setBeverages(Set.of(beverage1Id, beverage2Id));

        User expected = new User();
        expected.setName("Poc");
        expected.setLastName("Local");
        expected.setEmail("poc@gmail.com");
        expected.setUserName("localpoc");
        expected.setPassword("Password123");
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
        Beverage beverage1 = new Beverage(1L,"Gin","London");
        Beverage beverage2 = new Beverage(2L,"Whiskey","Irish");

        BeverageDto beverageDto1 = new BeverageDto(1L,"Gin","London");
        BeverageDto beverageDto2 = new BeverageDto(2L,"Whiskey","Irish");

        UserDto userDto = new UserDto(null,
                "Poc",
                "Local",
                "poc@gmail.com",
                "localpoc",
                "Password123",
                List.of(beverageDto1, beverageDto2));

        User expected = new User(null,
                "Poc",
                "Local",
                "poc@gmail.com",
                "localpoc",
                "Password123",
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
        Beverage beverage1 = new Beverage(1L,"Gin","London");
        Beverage beverage2 = new Beverage(2L,"Whiskey","Irish");
        String beveragesName = "Gin, Whiskey";

        User user = new User(3L,
                "Poc",
                "Local",
                "poc@gmail.com",
                "localpoc",
                "Password123",
                null,
                Set.of(beverage1, beverage2),
                null);

        UserViewDto expected = new UserViewDto(3L,
                "Poc",
                "Local",
                "poc@gmail.com",
                beveragesName);

        // When
        UserViewDto actual = userConverter.userToUserViewDto(user);

        // Then
        //TODO: Should I use "usingRecursiveComparison()" here?
        assertThat(actual).isEqualTo(expected);
    }
}
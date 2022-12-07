package com.ivan.alcomeeting.converter;


import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.dto.view.UserViewDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Role;
import com.ivan.alcomeeting.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class UserConverter {

    private final BeverageConverter beverageConverter;

    @Autowired
    public UserConverter(BeverageConverter beverageConverter) {
        this.beverageConverter = beverageConverter;
    }

    public UserDto userToUserDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setBeverages(user.getBeverages().stream()
                .map(beverageConverter::beverageToBeverageDto)
                .sorted(Comparator.comparing(BeverageDto::getName))
                .collect(Collectors.toList()));

        return userDto;
    }

    public User userCreationDtoToUser(UserCreationDto userCreationDto,
                                      List<Beverage> beverages,
                                      Set<Role> roles){
        User user = new User();

        user.setName(userCreationDto.getName());
        user.setLastName(userCreationDto.getLastName());
        user.setEmail(userCreationDto.getEmail());
        user.setUserName(userCreationDto.getUserName());
        user.setPassword(userCreationDto.getPassword());
        user.setBeverages(new HashSet<>(beverages));
        user.setRoles(roles);

        return user;

    }

    public User userDtoToUser(UserDto userDto){
        User user = new User();

        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setBeverages(userDto.getBeverages().stream()
                .map(beverageConverter::beverageDtoToBeverage)
                .collect(Collectors.toSet()));

        return user;

    }

    public UserViewDto userToUserViewDto(User user){
        UserViewDto userViewDto = new UserViewDto();

        userViewDto.setId(user.getId());
        userViewDto.setName(user.getName());
        userViewDto.setLastName(user.getLastName());
        userViewDto.setEmail(user.getEmail());
        userViewDto.setBeverages(user.getBeverages().stream()
                                                    .map(Beverage::getName)
                                                    .collect(Collectors.joining(", ")));
        return userViewDto;
    }

}

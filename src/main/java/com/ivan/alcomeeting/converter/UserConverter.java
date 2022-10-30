package com.ivan.alcomeeting.converter;

import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                                                .map(beverage -> beverage.getId())
                                                .collect(Collectors.toSet()));

        return userDto;
    }


    public User userDtoToUser(UserDto userDto){

        User user = new User();

        user.setName(userDto.getName());
        user.setLastName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setBeverages(userDto.getBeverages().stream()
                .map(beverageId -> new Beverage(beverageId, null, null))
                .collect(Collectors.toSet()));

        return user;

    }

}

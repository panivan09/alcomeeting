package com.ivan.alcomeeting.converter.toentity;

import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserEntityConverter {

    private final BeverageEntityConverter beverageEntityConverter;

    @Autowired
    public UserEntityConverter(BeverageEntityConverter beverageEntityConverter) {
        this.beverageEntityConverter = beverageEntityConverter;
    }

    public User userDtoToUser(UserDto userDto){

        User user = new User();

        user.setName(userDto.getName());
        user.setLastName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setBeverages(userDto.getBeveragesDto().stream()
                                                    .map(beverageEntityConverter::beverageDtoToBeverage)
                                                    .collect(Collectors.toSet()));

        return user;

    }
}

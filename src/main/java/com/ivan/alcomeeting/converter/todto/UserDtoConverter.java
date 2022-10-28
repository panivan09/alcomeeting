package com.ivan.alcomeeting.converter.todto;

import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class UserDtoConverter {

    private final BeverageDtoConverter beverageDtoConverter;

    @Autowired
    public UserDtoConverter(BeverageDtoConverter beverageDtoConverter) {
        this.beverageDtoConverter = beverageDtoConverter;
    }

    public UserDto userToUserDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastname(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setBeveragesDto(user.getBeverages().stream()
                                                .map(beverageDtoConverter::beverageToBeverageDto)
                                                .collect(Collectors.toList()));

        return userDto;
    }
}

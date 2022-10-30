package com.ivan.alcomeeting.service;

import com.ivan.alcomeeting.converter.UserConverter;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public UserDto saveUser(UserDto userDto){
        User saveUser = userRepository.save(userConverter.userDtoToUser(userDto));
        return userConverter.userToUserDto(saveUser);
    }

    public UserDto saveUser(User user){
        User saveUser = userRepository.save(user);
        return userConverter.userToUserDto(saveUser);
    }


    public User getUserById(Long userId){
        return userRepository.findById(userId).get();
    }
}

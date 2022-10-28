package com.ivan.alcomeeting.service;

import com.ivan.alcomeeting.converter.toentity.UserEntityConverter;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserEntityConverter userEntityConverter;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserEntityConverter userEntityConverter) {
        this.userRepository = userRepository;
        this.userEntityConverter = userEntityConverter;
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public UserDto saveUser(UserDto userDto){

        return userRepository.save(userEntityConverter.userDtoToUser(userDto));
    }


    public User getUserById(Long userId){
        return userRepository.findById(userId).get();
    }
}

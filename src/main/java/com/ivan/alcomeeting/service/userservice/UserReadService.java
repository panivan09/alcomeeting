package com.ivan.alcomeeting.service.userservice;

import com.ivan.alcomeeting.converter.UserConverter;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.dto.UserUpdateDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserReadService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserReadService(UserRepository userRepository,
                           UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userConverter::userToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long userId) {
        User userEntity = getUserEntityById(userId);

        return userConverter.userToUserDto(userEntity);
    }

    protected List<User> getUserEntitiesByIds(Collection<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    public User getUserByUserName(String userName) {
        Optional<User> userEntity = userRepository.findUserByUserName(userName);
        if (userEntity.isEmpty()) {
            throw new ValidationException("This user doesn't exist by name: " + userName);
        }
        return userEntity.get();
    }

    public User getUserEntityById(Long userId) {
        Optional<User> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new ValidationException("This user doesn't exist by Id: " + userId);
        }
        return userEntity.get();
    }

}

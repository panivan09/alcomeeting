package com.ivan.alcomeeting.service;

import com.ivan.alcomeeting.converter.UserConverter;
import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.dto.UserUpdateDto;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userConverter::userToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long userId){
        User userEntity = findUserById(userId);

        return userConverter.userToUserDto(userEntity);
    }

    @Transactional
    public UserDto createUser(UserCreationDto userCreationDto){
        User createUserEntity = userConverter.userCreationDtoToUser(userCreationDto);

        userRepository.save(createUserEntity);

        return userConverter.userToUserDto(createUserEntity);
        //return getUserById(createUserEntity.getId());
    }

    public UserDto updateUser(UserUpdateDto userUpdate) {
        User existedUser = findUserById(userUpdate.getId());

        existedUser.setName(userUpdate.getName());
        existedUser.setLastName(userUpdate.getLastName());
        existedUser.setEmail(userUpdate.getEmail());
        existedUser.setPassword(userUpdate.getPassword());

        userRepository.save(existedUser);

        return userConverter.userToUserDto(existedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {

        userRepository.deleteUserFromMeetingsUsers(userId);
        userRepository.deleteUserFromUsersBeverages(userId);
        userRepository.deleteById(userId);
    }


    private User findUserById(Long userId) {
        Optional<User> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()){
            throw new IllegalStateException("This user doesn't exist by Id: " + userId);
        }
        return userEntity.get();
    }


}

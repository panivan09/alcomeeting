package com.ivan.alcomeeting.service.userservice;

import com.ivan.alcomeeting.converter.UserConverter;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.dto.UserUpdateDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.UserRepository;
import com.ivan.alcomeeting.service.BeverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUpdateService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final BeverageService beverageService;
    private final UserSecurityService userSecurityService;
    private final UserReadService userReadService;

    @Autowired
    public UserUpdateService(UserRepository userRepository,
                             UserConverter userConverter,
                             BeverageService beverageService,
                             UserSecurityService userSecurityService,
                             UserReadService userReadService) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.beverageService = beverageService;
        this.userSecurityService = userSecurityService;
        this.userReadService = userReadService;
    }

    public UserDto updateUser(UserUpdateDto userUpdate) {
        User existedUser = userReadService.getUserEntityById(userUpdate.getId());

        setUserFieldsNotNull(userUpdate, existedUser);

        existedUser.setName(userUpdate.getName());
        existedUser.setLastName(userUpdate.getLastName());
        existedUser.setEmail(userUpdate.getEmail());
        existedUser.setPassword(userUpdate.getPassword());

        userRepository.save(existedUser);

        return userConverter.userToUserDto(existedUser);
    }

    public UserDto addBeverage(Long userId, Long beverageId) {
        User currentUser = userReadService.getUserEntityById(userId);
        Beverage beverageToAdd = beverageService.getBeverageEntityById(beverageId);

        currentUser.getBeverages().add(beverageToAdd);
        userRepository.save(currentUser);

        return userConverter.userToUserDto(currentUser);

    }

    public UserDto deleteBeverage(Long userId, Long beverageId) {
        User currentUser = userReadService.getUserEntityById(userId);
        Beverage beverageToDelete = beverageService.getBeverageEntityById(beverageId);

        currentUser.getBeverages().remove(beverageToDelete);
        userRepository.save(currentUser);

        return userConverter.userToUserDto(currentUser);
    }

    private void setUserFieldsNotNull(UserUpdateDto userUpdateDto, User existUser){
        String name = userUpdateDto.getName();
        String lastName = userUpdateDto.getLastName();
        String email = userUpdateDto.getEmail();
        String password = userUpdateDto.getPassword();

        if (name == null || name.isEmpty()){
            userUpdateDto.setName(existUser.getName());
        }

        if (lastName == null || lastName.isEmpty()){
            userUpdateDto.setLastName(existUser.getLastName());
        }

        if (email == null || email.isEmpty()){
            userUpdateDto.setEmail(existUser.getEmail());
        }

        if (password == null || password.isEmpty()){
            userUpdateDto.setPassword(existUser.getPassword());
        }else {
            userUpdateDto.setPassword(userSecurityService.encodePassword(password));
        }

    }

}

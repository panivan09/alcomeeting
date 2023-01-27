package com.ivan.alcomeeting.service.user;

import com.ivan.alcomeeting.converter.UserConverter;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.dto.UserFullNameDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.UserRepository;
import com.ivan.alcomeeting.service.BeverageService;
import com.ivan.alcomeeting.validation.UserEmailValidator;
import com.ivan.alcomeeting.validation.UserPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUpdateService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final BeverageService beverageService;
    private final UserSecurityService userSecurityService;
    private final UserReadService userReadService;
    private final UserPasswordValidator userPasswordValidator;
    private final UserEmailValidator userEmailValidator;

    @Autowired
    public UserUpdateService(UserRepository userRepository,
                             UserConverter userConverter,
                             BeverageService beverageService,
                             UserSecurityService userSecurityService,
                             UserReadService userReadService,
                             UserPasswordValidator userPasswordValidator,
                             UserEmailValidator userEmailValidator) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.beverageService = beverageService;
        this.userSecurityService = userSecurityService;
        this.userReadService = userReadService;
        this.userPasswordValidator = userPasswordValidator;
        this.userEmailValidator = userEmailValidator;
    }

    public UserDto updateUserFullName(Long userId, UserFullNameDto userUpdate) {
        User existingUser = userReadService.getUserEntityById(userId);
        // check if userUpdate has the same values as existing. If yes - do nothing
        updateUserFullName(userUpdate, existingUser);

        userRepository.save(existingUser);

        return userConverter.userToUserDto(existingUser);
    }

    public UserDto updateUserPassword(Long userId, String password) {
        userPasswordValidator.validate(password);

        User existedUser = userReadService.getUserEntityById(userId);
        existedUser.setPassword(userSecurityService.encodePassword(password));

        userRepository.save(existedUser);

        return userConverter.userToUserDto(existedUser);
    }

    public UserDto updateUserEmail(Long userId, String email) {
        userEmailValidator.validate(email);

        User existedUser = userReadService.getUserEntityById(userId);
        existedUser.setEmail(email);

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

    private void updateUserFullName(UserFullNameDto userFullNameDto, User existingUser){
        String name = userFullNameDto.getName();
        String lastName = userFullNameDto.getLastName();

        if (name != null){
            existingUser.setName(name);
        }

        if (lastName != null){
            existingUser.setLastName(lastName);
        }
    }
}

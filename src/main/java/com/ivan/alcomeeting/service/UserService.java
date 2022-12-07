package com.ivan.alcomeeting.service;

import com.ivan.alcomeeting.converter.UserConverter;
import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.dto.UserUpdateDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Role;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.BeverageRepository;
import com.ivan.alcomeeting.repository.RoleRepository;
import com.ivan.alcomeeting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final long USER_ROLE_ID = 1L;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final BeverageService beverageService;
    private final RoleRepository roleRepository;
    private final BeverageRepository beverageRepository;
    private final UserSecurityService userSecurityService;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserConverter userConverter,
                       BeverageService beverageService,
                       RoleRepository roleRepository,
                       BeverageRepository beverageRepository,
                       UserSecurityService userSecurityService
    ) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.beverageService = beverageService;
        this.roleRepository = roleRepository;
        this.beverageRepository = beverageRepository;
        this.userSecurityService = userSecurityService;

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

    public UserDto createUser(UserCreationDto userCreationDto) {
        List<Beverage> beverages = userCreationDto.getBeverages().stream()
                .map(beverageRepository::getReferenceById)
                .collect(Collectors.toList());

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getReferenceById(USER_ROLE_ID));

        userCreationDto.setPassword(userSecurityService.encodePassword(userCreationDto.getPassword()));
        User createUserEntity = userConverter.userCreationDtoToUser(
                userCreationDto,
                beverages,
                roles
        );

        userRepository.save(createUserEntity);

        return userConverter.userToUserDto(createUserEntity);
    }

    public UserDto updateUser(UserUpdateDto userUpdate) {
        User existedUser = getUserEntityById(userUpdate.getId());

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


    public UserDto addBeverage(Long userId, Long beverageId) {
        User currentUser = getUserEntityById(userId);
        Beverage beverageToAdd = beverageService.getBeverageEntityById(beverageId);

        currentUser.getBeverages().add(beverageToAdd);
        userRepository.save(currentUser);

        return userConverter.userToUserDto(currentUser);

    }

    public UserDto deleteBeverage(Long userId, Long beverageId) {
        User currentUser = getUserEntityById(userId);
        Beverage beverageToDelete = beverageService.getBeverageEntityById(beverageId);

        currentUser.getBeverages().remove(beverageToDelete);
        userRepository.save(currentUser);

        return userConverter.userToUserDto(currentUser);
    }


    protected List<User> getUserEntitiesByIds(Collection<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    protected User getUserByUserName(String userName) {
        Optional<User> userEntity = userRepository.findUserByUserName(userName);
        if (userEntity.isEmpty()) {
            throw new IllegalStateException("This user doesn't exist by name: " + userName);
        }
        return userEntity.get();
    }

    protected User getUserEntityById(Long userId) {
        Optional<User> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new IllegalStateException("This user doesn't exist by Id: " + userId);
        }
        return userEntity.get();
    }

}

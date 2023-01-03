package com.ivan.alcomeeting.service.userservice;

import com.ivan.alcomeeting.converter.UserConverter;
import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.dto.UserDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.Role;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.repository.BeverageRepository;
import com.ivan.alcomeeting.repository.RoleRepository;
import com.ivan.alcomeeting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserCreationService {
    private static final long USER_ROLE_ID = 1L;

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RoleRepository roleRepository;
    private final BeverageRepository beverageRepository;
    private final UserSecurityService userSecurityService;
//    private final UserCreationValidator userCreationValidator;

    @Autowired
    public UserCreationService(UserRepository userRepository,
                               UserConverter userConverter,
                               RoleRepository roleRepository,
                               BeverageRepository beverageRepository,
                               UserSecurityService userSecurityService
//                               UserCreationValidator userCreationValidator
) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.roleRepository = roleRepository;
        this.beverageRepository = beverageRepository;
        this.userSecurityService = userSecurityService;
//        this.userCreationValidator = userCreationValidator;
    }

    public UserDto createUser(UserCreationDto userCreationDto) throws ValidationException {
//        userCreationValidator.validate(userCreationDto); - using for view
        checkUserNameIsFree(userCreationDto.getUserName());
        checkUserEmailIsFree(userCreationDto.getEmail());

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

    private void checkUserEmailIsFree(String email) {
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        userByEmail.ifPresent(a -> { throw new ValidationException("Email is occupied");});
    }

    private void checkUserNameIsFree(String userName) {
        Optional<User> userByUserName = userRepository.findUserByUserName(userName);
        userByUserName.ifPresent(a -> { throw new ValidationException("User name is occupied");});
    }
}

package com.ivan.alcomeeting.service.view;

import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserViewService {

    private final UserRepository userRepository;

    @Autowired
    public UserViewService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




    public User getUserByUserName(String userName) {
        Optional<User> userEntity = userRepository.findUserByUserName(userName);
        if (userEntity.isEmpty()) {
            throw new IllegalStateException("This user doesn't exist by name: " + userName);
        }
        return userEntity.get();
    }

    public String getLoggedUserFullName(String userName) {
        User userEntity = getUserByUserName(userName);

        return userEntity.getName() + " " + userEntity.getLastName();
    }
}

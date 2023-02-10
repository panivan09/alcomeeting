package com.ivan.alcomeeting.service.user;

import com.ivan.alcomeeting.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDeleteService {

    private final UserRepository userRepository;

    public UserDeleteService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteUserFromMeetingsUsers(userId);
        userRepository.deleteUserFromUsersBeverages(userId);
        userRepository.deleteById(userId);
    }
}

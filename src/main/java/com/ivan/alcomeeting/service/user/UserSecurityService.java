package com.ivan.alcomeeting.service.user;

import com.ivan.alcomeeting.converter.UserSecurityConverter;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserSecurityConverter userSecurityConverter;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserSecurityService(UserRepository userRepository,
                               UserSecurityConverter userSecurityConverter,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userSecurityConverter = userSecurityConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> userEntity = userRepository.findUserByUserName(userName);
        if (userEntity.isEmpty()){
            throw new UsernameNotFoundException("User by " + userName + " - not found");
        }
        return userSecurityConverter.userToUserDetails(userEntity.get());
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
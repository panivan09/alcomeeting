package com.ivan.alcomeeting.service.user;

import com.ivan.alcomeeting.converter.UserSecurityConverter;
import com.ivan.alcomeeting.entity.Permission;
import com.ivan.alcomeeting.entity.Role;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class UserSecurityServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserSecurityConverter userSecurityConverter;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserSecurityService userSecurityService;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_returnUserDetailsIfUserByUserNameExist() {
        //Given
        String userName = "UserName";
        String userPassword = "Any";

        String permissionOneName = "READ";
        String permissionTwoName = "WRITE";
        Permission permissionOne = new Permission(1L, permissionOneName);
        Permission permissionTwo = new Permission(2L, permissionTwoName);
        Set<Permission> permissions = Set.of(permissionOne, permissionTwo);

        String roleName = "USER";
        Role role = new Role(1L, roleName, "Low access", permissions);
        Set<Role> roles = Set.of(role);

        User userEntity = new User(3L,
                "Any",
                "Any",
                "Any",
                userName,
                userPassword,
                null,
                null,
                null,
                roles);

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = Set.of(new SimpleGrantedAuthority(permissionOneName),
                new SimpleGrantedAuthority(permissionTwoName),
                new SimpleGrantedAuthority("ROLE_" + roleName));

        UserDetails expectedUserDetails = org.springframework.security.core.userdetails.User.builder()
        .authorities(simpleGrantedAuthorities)
                .password(userPassword)
                .username(userName)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();

        when(userRepository.findUserByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(userSecurityConverter.userToUserDetails(userEntity)).thenReturn(expectedUserDetails);

        //When
        UserDetails actualUserDetails = userSecurityService.loadUserByUsername(userName);

        //Then
        assertThat(actualUserDetails).isEqualTo(expectedUserDetails);
    }

    @Test
    void loadUserByUsername_throwEntityNotFoundExceptionWhenUserRepositoryHasNoUser() {
        //Given
        String userName = "UserName";
        String exceptionMessage = "User by " + userName + " - not found";

        when(userRepository.findUserByUserName(userName)).thenReturn(Optional.empty());

        //When and Then
        assertThatThrownBy(() -> userSecurityService.loadUserByUsername(userName))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void encodePassword_returnEncodePassword() {
        //Given
        String password = "password";
        String expectedEncodePassword = "p1";

        when(passwordEncoder.encode(password)).thenReturn(expectedEncodePassword);

        //When
        String actualEncodePassword = userSecurityService.encodePassword(password);

        //Then
        assertThat(actualEncodePassword).isEqualTo(expectedEncodePassword);
    }
}
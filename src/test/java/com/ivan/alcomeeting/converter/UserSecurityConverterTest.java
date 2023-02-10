package com.ivan.alcomeeting.converter;

import com.ivan.alcomeeting.entity.Permission;
import com.ivan.alcomeeting.entity.Role;
import com.ivan.alcomeeting.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserSecurityConverterTest {
    @InjectMocks
    private UserSecurityConverter userSecurityConverter;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void userToUserDetails_returnUserDetailsAfterConversion() {
        // Given
        String userName = "userName";
        String password = "Password123";
        String permissionOneName = "READ";
        String permissionTwoName = "UPDATE";

        Permission permission1 = new Permission(1L, permissionOneName);
        Permission permission2 = new Permission(2L, permissionTwoName);
        Set<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Role role = new Role(1L, "USER", "lowe access", permissionSet);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = new HashSet<>();
        simpleGrantedAuthoritySet.add(new SimpleGrantedAuthority(permissionOneName));
        simpleGrantedAuthoritySet.add(new SimpleGrantedAuthority(permissionTwoName));
        simpleGrantedAuthoritySet.add(new SimpleGrantedAuthority("ROLE_USER"));

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setRoles(roleSet);

        UserDetails expected = org.springframework.security.core.userdetails.User.builder()
                .authorities(simpleGrantedAuthoritySet)
                .password(password)
                .username(userName)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();

        // When
        UserDetails actual = userSecurityConverter.userToUserDetails(user);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
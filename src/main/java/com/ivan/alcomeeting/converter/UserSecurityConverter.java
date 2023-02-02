package com.ivan.alcomeeting.converter;

import com.ivan.alcomeeting.entity.Role;
import com.ivan.alcomeeting.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserSecurityConverter {

    public UserDetails userToUserDetails(User userEntity){
        UserDetails build = org.springframework.security.core.userdetails.User.builder()
                .authorities(userEntity.getRoles().stream()
                        .map(this::getAuthorities)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet()))
                .password(userEntity.getPassword())
                .username(userEntity.getUserName())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
        return build;
    }

    private Set<SimpleGrantedAuthority> getAuthorities(Role role){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        role.getPermissions()
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

        return authorities;
    }
}

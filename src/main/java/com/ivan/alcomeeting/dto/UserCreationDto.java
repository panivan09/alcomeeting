package com.ivan.alcomeeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Set<Long> beverages;


}

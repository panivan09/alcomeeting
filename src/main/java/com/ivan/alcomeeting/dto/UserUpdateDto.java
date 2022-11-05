package com.ivan.alcomeeting.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
}

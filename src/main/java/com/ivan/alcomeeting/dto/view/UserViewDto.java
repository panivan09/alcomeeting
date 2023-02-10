package com.ivan.alcomeeting.dto.view;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserViewDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String beverages;
}

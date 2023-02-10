package com.ivan.alcomeeting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private List<BeverageDto> beverages;

}

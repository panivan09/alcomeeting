package com.ivan.alcomeeting.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFullNameDto {

    @Size(min = 1, max = 50, message = "User name must be between 1 and 50 characters")
    @NotEmpty
    private String name;

    @Size(min = 1, max = 50, message = "User lastname must be between 1 and 50 characters")
    @NotEmpty
    private String lastName;
}

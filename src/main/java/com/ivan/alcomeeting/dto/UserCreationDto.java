package com.ivan.alcomeeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {

    private Long id;

    @NotEmpty(message = "User name should not be empty or null")
    @Size(min = 1, max = 50, message = "User name must be between 1 and 50 characters")
    private String name;

    @NotEmpty(message = "User lastname should not be null or empty")
    @Size(min = 1, max = 50, message = "User lastname must be between 1 and 50 characters")
    private String lastName;

    @NotEmpty(message = "User Email should not be null or empty")
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Should be valid email")
    private String email;

    @NotEmpty(message = "User Nickname should not be null or empty")
    @Size(min = 1, max = 50, message = "User Nickname must be between 1 and 50 characters")
    private String userName;

    @NotEmpty(message = "User Password should not be null or empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=\\S+$).{8,20}$", message = "Password must contain at least 8 " +
                                                                    "characters, including UPPER/lowercase and numbers")
    private String password;

    @NotEmpty(message = "User Beverages should not be null or empty")
    private Set<Long> beverages;

}

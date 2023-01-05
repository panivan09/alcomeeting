package com.ivan.alcomeeting.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.ivan.alcomeeting.validation.view.ValidationUtils.validateNotNullOrEmpty;
import static com.ivan.alcomeeting.validation.view.ValidationUtils.validateStringByPattern;

@Component
public class UserPasswordValidator {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=\\S+$).{8,20}$");

    public void validate(String password) {
        validateNotNullOrEmpty(password, "User password should not be null or empty");

        validateStringByPattern(password, PASSWORD_PATTERN, "Password must contain at least 8 characters," +
                " including UPPER/lowercase and numbers");
    }
}

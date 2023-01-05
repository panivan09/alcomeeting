package com.ivan.alcomeeting.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.ivan.alcomeeting.validation.view.ValidationUtils.validateNotNullOrEmpty;
import static com.ivan.alcomeeting.validation.view.ValidationUtils.validateStringByPattern;

@Component
public class UserEmailValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public void validate(String email) {
        validateNotNullOrEmpty(email, "User email should not be null or empty");

        validateStringByPattern(email, EMAIL_PATTERN, "Incorrect email");
    }
}

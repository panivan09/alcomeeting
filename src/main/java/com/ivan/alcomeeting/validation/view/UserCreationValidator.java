package com.ivan.alcomeeting.validation.view;


import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.ivan.alcomeeting.validation.view.ValidationUtils.*;

@Component
public class UserCreationValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=\\S+$).{8,20}$");

    public void validate(UserCreationDto userCreation) throws ValidationException {

        String email = userCreation.getEmail();
        String password = userCreation.getPassword();

        validateNotNullOrEmpty(userCreation.getName(), "User name should not be null or empty");

        validateNotNullOrEmpty(userCreation.getLastName(), "User last name should not be null or empty");

        validateNotNullOrEmpty(email, "User email should not be null or empty");

        validateStringByPattern(email, EMAIL_PATTERN, "Incorrect email");

        validateNotNullOrEmpty(userCreation.getUserName(), "User nickname should not be null or empty");

        validateNotNullOrEmpty(password, "User password should not be null or empty");

        validateStringByPattern(password, PASSWORD_PATTERN, "Password must contain at least 8 characters," +
                                                                    " including UPPER/lowercase and numbers");

        validateCollectionNotNullOrEmpty(userCreation.getBeverages(), "User beverages should not be null or empty");
    }
}

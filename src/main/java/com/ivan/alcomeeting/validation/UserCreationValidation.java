package com.ivan.alcomeeting.validation;


import com.ivan.alcomeeting.dto.UserCreationDto;
import com.ivan.alcomeeting.entity.User;
import com.ivan.alcomeeting.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class UserCreationValidation {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=\\S+$).{8,20}$");

    public void isValid(UserCreationDto userCreation,
                        Optional<User> userByUsername,
                        Optional<User> userByEmail) throws ValidationException {

        String email = userCreation.getEmail(); // check valid email by regex pxatternx
        String password = userCreation.getPassword();

        ValidationUtils.validateNotNullOrEmpty(userCreation.getName(), "User name should not be null or empty");

        ValidationUtils.validateNotNullOrEmpty(userCreation.getLastName(), "User last name should not be null or empty");

        ValidationUtils.validateNotNullOrEmpty(email, "User email should not be null or empty");

        if (!EMAIL_PATTERN.matcher(email).matches()){
            throw new ValidationException("Incorrect email");
        }

        if (userByEmail.isPresent()){
            throw new ValidationException("This email has already been used");
        }

        ValidationUtils.validateNotNullOrEmpty(userCreation.getUserName(), "User nickname should not be null or empty");

        if (userByUsername.isPresent()){
            throw new ValidationException("User the same nickname already exist");
        }

        ValidationUtils.validateNotNullOrEmpty(password, "User password should not be null or empty");

        if (!PASSWORD_PATTERN.matcher(password).matches()){
            throw new ValidationException("Incorrect password");
        }

        ValidationUtils.validateCollectionNotNullOrEmpty(userCreation.getBeverages(), "User beverages should not be null or empty");

    }

}

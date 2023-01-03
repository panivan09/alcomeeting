package com.ivan.alcomeeting.validation.view;

import com.ivan.alcomeeting.exception.ValidationException;

import java.util.Collection;
import java.util.regex.Pattern;


public class ValidationUtils {
    public static void validateNotNullOrEmpty(String value, String message) throws ValidationException {
        if (value == null || value.isEmpty()){
            throw new ValidationException(message);
        }

    }

    public static void validateCollectionNotNullOrEmpty(Collection value, String message) throws ValidationException {
        if (value == null || value.isEmpty()){
            throw new ValidationException(message);
        }
    }

    public static void validateStringByPattern(String value, Pattern pattern, String message){
        if (!pattern.matcher(value).matches()){
            throw new ValidationException(message);
        }
    }
}

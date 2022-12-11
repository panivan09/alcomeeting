package com.ivan.alcomeeting.validation;

import com.ivan.alcomeeting.exception.ValidationException;

import java.util.Collection;


public class ValidationUtils {
    public static void validateNotNullOrEmpty(String value, String message) throws ValidationException {
        if (value == null || value.isEmpty()){
            throw new ValidationException(message);
        }

    }

    public static void validateCollectionNotNullOrEmpty(Collection<?> value, String message) throws ValidationException {
        if (value == null || value.isEmpty()){
            throw new ValidationException(message);
        }

    }
}

package com.ivan.alcomeeting.validation.view;

import com.ivan.alcomeeting.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MeetingSearchValidator {

    public void validate(String beverage) throws ValidationException {
        ValidationUtils.validateNotNullOrEmpty(beverage, "Beverage should not be null or empty" );
    }

    public void isDateValid(LocalDate date) throws ValidationException {
        if (date == null) {
            throw new ValidationException("Date should not be null or empty");
        }
    }

    public void isAddressValid(String address) throws ValidationException {
        ValidationUtils.validateNotNullOrEmpty(address, "Address should not be null or empty" );
    }
}

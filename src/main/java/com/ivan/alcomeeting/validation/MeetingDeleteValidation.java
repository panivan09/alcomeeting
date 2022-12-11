package com.ivan.alcomeeting.validation;

import com.ivan.alcomeeting.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class MeetingDeleteValidation {

    public void isValid(Long meetingId) throws ValidationException {
        if (meetingId == null){
            throw new ValidationException("Meeting id should not be null or empty");
        }
    }

}

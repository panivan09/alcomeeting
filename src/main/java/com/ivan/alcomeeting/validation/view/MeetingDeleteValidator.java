package com.ivan.alcomeeting.validation.view;

import com.ivan.alcomeeting.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class MeetingDeleteValidator {

    public void validate(Long meetingId) throws ValidationException {
        if (meetingId == null){
            throw new ValidationException("Meeting id should not be null or empty");
        }
    }

}

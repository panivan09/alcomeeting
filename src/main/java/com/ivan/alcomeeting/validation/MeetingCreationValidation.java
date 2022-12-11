package com.ivan.alcomeeting.validation;

import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class MeetingCreationValidation {

    public void isValid(MeetingCreationDto meetingCreationDto) throws ValidationException {

        ValidationUtils.validateNotNullOrEmpty(meetingCreationDto.getName(), "Meeting name should not be null or empty");

        ValidationUtils.validateNotNullOrEmpty(meetingCreationDto.getDate(), "Meeting date should not be null or empty");

        ValidationUtils.validateNotNullOrEmpty(meetingCreationDto.getAddress(), "Meeting address should not be null or empty");

        if (meetingCreationDto.getMeetingOwner() == null) {
            throw new ValidationException("Meeting owner should not be null or empty");
        }

        ValidationUtils.validateCollectionNotNullOrEmpty(meetingCreationDto.getBeverages(), "Meeting beverages should not be null or empty");

    }
}

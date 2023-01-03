package com.ivan.alcomeeting.validation.view;

import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.exception.ValidationException;
import org.springframework.stereotype.Component;

import static com.ivan.alcomeeting.validation.view.ValidationUtils.validateCollectionNotNullOrEmpty;
import static com.ivan.alcomeeting.validation.view.ValidationUtils.validateNotNullOrEmpty;

@Component
public class MeetingCreationValidator {

    public void validate(MeetingCreationDto meetingCreationDto) throws ValidationException {
        validateNotNullOrEmpty(meetingCreationDto.getName(), "Meeting name should not be null or empty");

        validateNotNullOrEmpty(meetingCreationDto.getDate(), "Meeting date should not be null or empty");

        validateNotNullOrEmpty(meetingCreationDto.getAddress(), "Meeting address should not be null or empty");

        validateCollectionNotNullOrEmpty(meetingCreationDto.getBeverages(), "Meeting beverages should not be null or empty");
    }
}

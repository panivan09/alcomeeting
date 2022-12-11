package com.ivan.alcomeeting.validation;

import com.ivan.alcomeeting.dto.MeetingUpdateDto;
import com.ivan.alcomeeting.entity.Meeting;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class MeetingUpdateValidation {

    public void isValid(MeetingUpdateDto meetingUpdateDto, Meeting meetingEntity){

        String name = meetingUpdateDto.getName();
        String date = meetingUpdateDto.getDate();
        String address = meetingUpdateDto.getAddress();

        if (name == null || name.isEmpty()){
            meetingUpdateDto.setName(meetingEntity.getName());
        }

        if (date == null || date.isEmpty()){
            String format = meetingEntity.getDate().format(DateTimeFormatter.ISO_DATE_TIME);
            meetingUpdateDto.setDate(format);
        }

        if (address == null || address.isEmpty()){
            meetingUpdateDto.setAddress(meetingEntity.getAddress());
        }

    }
}

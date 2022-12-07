package com.ivan.alcomeeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingCreationDto {

    private Long id;
    private String name;
    private String date;
    private String address;
    private Long meetingOwner;
    private Set<Long> beverages;


}

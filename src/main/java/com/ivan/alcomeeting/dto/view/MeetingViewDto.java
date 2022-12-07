package com.ivan.alcomeeting.dto.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingViewDto {
    private Long id;
    private String name;
    private String date;
    private String address;
    private String meetingOwner;
    private String listParticipates;
    private String listBeverages;
    private boolean isOwner;
}

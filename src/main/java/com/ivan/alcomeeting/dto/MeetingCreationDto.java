package com.ivan.alcomeeting.dto;

import com.ivan.alcomeeting.entity.Beverage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingCreationDto {

    private Long id;
    private String name;
    private LocalDate date;
    private String address;
    private Long meetingOwner;
    private Set<Long> participates;
    private Set<Long> beveragesDto;


}

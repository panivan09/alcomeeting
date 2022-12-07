package com.ivan.alcomeeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDto {
    private Long id;
    private String name;
    private String date;
    private String address;
    private UserDto meetingOwner;
    private List<UserDto> participates;
    private List<BeverageDto> beveragesDto;
}

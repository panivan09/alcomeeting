package com.ivan.alcomeeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingUpdateDto {

    private Long id;
    private String name;
    private String date;
    private String address;


}

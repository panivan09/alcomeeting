package com.ivan.alcomeeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingUpdateDto {

    private Long id;
    private String name;
    private String address;
    private LocalDate date;


}

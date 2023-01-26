package com.ivan.alcomeeting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingCreationDto {

    private Long id;

    @NotEmpty(message = "Meeting Name should not be null or empty")
    private String name;

    @NotEmpty(message = "Meeting Date should not be null or empty")
    private String date;

    @NotEmpty(message = "Meeting Address should not be null or empty")
    private String address;

    @NotNull(message ="Meeting Owner should not be null or empty")
    @Min(value = 1, message = "Meeting Owner should not be < 0 or 0")
    private Long meetingOwner;

    @NotEmpty(message = "Meeting beverage should not be null or empty")
    private Set<Long> beverages;

}

package com.ivan.alcomeeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeverageDto {

    private Long id;
    private String name;
    private String description;

}

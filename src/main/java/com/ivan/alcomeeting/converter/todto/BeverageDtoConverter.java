package com.ivan.alcomeeting.converter.todto;

import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.entity.Beverage;
import org.springframework.stereotype.Component;

@Component
public class BeverageDtoConverter {

    public BeverageDto beverageToBeverageDto(Beverage beverage){

        BeverageDto beverageDto = new BeverageDto();

        beverageDto.setId(beverage.getId());
        beverageDto.setName(beverage.getName());
        beverageDto.setDescription(beverage.getDescription());

        return beverageDto;
    }
}

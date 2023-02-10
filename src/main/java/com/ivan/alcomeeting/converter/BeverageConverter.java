package com.ivan.alcomeeting.converter;

import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.entity.Beverage;
import org.springframework.stereotype.Component;

@Component
public class BeverageConverter {

    public BeverageDto beverageToBeverageDto(Beverage beverage){
        BeverageDto beverageDto = new BeverageDto();
        beverageDto.setId(beverage.getId());
        beverageDto.setName(beverage.getName());
        beverageDto.setDescription(beverage.getDescription());

        return beverageDto;
    }

    public Beverage beverageDtoToBeverage(BeverageDto beverageDto){
        Beverage beverage = new Beverage();
        beverage.setId(beverageDto.getId());
        beverage.setName(beverageDto.getName());
        beverage.setDescription(beverageDto.getDescription());

        return beverage;
    }
}

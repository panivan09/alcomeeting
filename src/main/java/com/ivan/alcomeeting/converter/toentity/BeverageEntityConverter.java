package com.ivan.alcomeeting.converter.toentity;

import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.entity.Beverage;
import org.springframework.stereotype.Component;

@Component
public class BeverageEntityConverter {

    public Beverage beverageDtoToBeverage(BeverageDto beverageDto){

        Beverage beverage = new Beverage();

        beverage.setName(beverageDto.getName());
        beverage.setDescription(beverageDto.getDescription());

        return beverage;
    }

}

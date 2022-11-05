package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.service.BeverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/beverage")
public class BeveragesController {
    // get all beverages +
    // get by id +
    // create beverage +
    // update beverage +
    // remove beverage +

    private final BeverageService beverageService;

    @Autowired
    public BeveragesController(BeverageService beverageService) {
        this.beverageService = beverageService;
    }

    @GetMapping("/bulk")
    public List<BeverageDto> getAllBeverages(){
        return beverageService.getAllBeverages();
    }

    @GetMapping("{beverageId}")
    public BeverageDto getBeverageById(@PathVariable("beverageId") Long beverageId){
        return beverageService.getBeverageById(beverageId);
    }

    @PostMapping
    public BeverageDto createBeverage(@RequestBody BeverageDto createBeverageDto){
        return beverageService.createBeverage(createBeverageDto);
    }

    @PutMapping()
    public BeverageDto updateBeverage(@RequestBody BeverageDto updateBeverage){
        return beverageService.updateBeverage(updateBeverage);
    }

    @DeleteMapping("{beverageId}")
    public void deleteBeverage(@PathVariable("beverageId") Long beverageId){
        beverageService.deleteBeverage(beverageId);
    }
}

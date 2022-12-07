package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.service.BeverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<BeverageDto> getAllBeverages(){
        return beverageService.getAllBeverages();
    }

    @GetMapping("{beverageId}")
    @PreAuthorize("hasAuthority('READ')")
    public BeverageDto getBeverageById(@PathVariable("beverageId") Long beverageId){
        return beverageService.getBeverageById(beverageId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE')")
    public BeverageDto createBeverage(@RequestBody BeverageDto createBeverageDto){
        return beverageService.createBeverage(createBeverageDto);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('UPDATE')")
    public BeverageDto updateBeverage(@RequestBody BeverageDto updateBeverage){
        return beverageService.updateBeverage(updateBeverage);
    }

    @DeleteMapping("{beverageId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void deleteBeverage(@PathVariable("beverageId") Long beverageId){
        beverageService.deleteBeverage(beverageId);
    }
}

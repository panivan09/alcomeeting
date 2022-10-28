package com.ivan.alcomeeting.service;

import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.repository.BeverageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeverageService {

    private final BeverageRepository beverageRepository;

    @Autowired
    public BeverageService(BeverageRepository beverageRepository) {
        this.beverageRepository = beverageRepository;
    }

    public List<Beverage> getAllBeverages(){
        Optional<Beverage> byId = beverageRepository.findById(123L);
        return beverageRepository.findAll();
    }

}

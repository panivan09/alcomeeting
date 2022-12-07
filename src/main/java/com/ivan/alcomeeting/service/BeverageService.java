package com.ivan.alcomeeting.service;

import com.ivan.alcomeeting.converter.BeverageConverter;
import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.repository.BeverageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BeverageService {

    private final BeverageRepository beverageRepository;
    private final BeverageConverter beverageConverter;

    @Autowired
    public BeverageService(BeverageRepository beverageRepository,
                           BeverageConverter beverageConverter) {
        this.beverageRepository = beverageRepository;
        this.beverageConverter = beverageConverter;
    }

    public List<BeverageDto> getAllBeverages(){
        return beverageRepository.findAll().stream()
                .map(beverageConverter::beverageToBeverageDto)
                .sorted(Comparator.comparing(BeverageDto::getName))
                .collect(Collectors.toList());

    }

    public BeverageDto getBeverageById(Long beverageId) {
       Beverage beverageById = getBeverageEntityById(beverageId);
       return beverageConverter.beverageToBeverageDto(beverageById);
    }

    public BeverageDto getBeverageByName(String beverageName) {
        Beverage beverageByName = getBeverageEntityByName(beverageName);
        return beverageConverter.beverageToBeverageDto(beverageByName);
    }

    public BeverageDto createBeverage(BeverageDto createBeverageDto) {
        Beverage saveBeverage = beverageConverter.beverageDtoToBeverage(createBeverageDto);
        beverageRepository.save(saveBeverage);

        return beverageConverter.beverageToBeverageDto(saveBeverage);
    }

    public BeverageDto updateBeverage(BeverageDto updatedBeverage) {

        Beverage existingBeverage = getBeverageEntityById(updatedBeverage.getId());

        existingBeverage.setName(updatedBeverage.getName());
        existingBeverage.setDescription(updatedBeverage.getDescription());

        beverageRepository.save(existingBeverage);

        return beverageConverter.beverageToBeverageDto(existingBeverage);
    }


    @Transactional
    public void deleteBeverage(Long beverageId) {

        beverageRepository.removeMeetingsBeverages(beverageId);
        beverageRepository.removeUsersBeverages(beverageId);
        beverageRepository.removeBeverage(beverageId);

    }

    protected List<Beverage> getBeverageEntitiesByIds(Collection<Long> beverageIds) {
        return beverageRepository.findAllById(beverageIds);
    }

    protected Beverage getBeverageEntityById(Long beverageId) {
        Optional<Beverage> beverageById = beverageRepository.findById(beverageId);
        if (beverageById.isEmpty()){
            throw new IllegalStateException("The beverage does not exist by Id: " + beverageId);
        }
        return beverageById.get();
    }

    public Beverage getBeverageEntityByName(String beverageName) {
        Optional<Beverage> beverageById = beverageRepository.findByName(beverageName);
        if (beverageById.isEmpty()){
            throw new IllegalStateException("The beverage does not exist by Id: " + beverageName);
        }
        return beverageById.get();
    }

}

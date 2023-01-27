package com.ivan.alcomeeting.service;

import com.ivan.alcomeeting.converter.BeverageConverter;
import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.exception.EntityNotFoundException;
import com.ivan.alcomeeting.repository.BeverageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BeverageServiceTest {
    @Mock
    private BeverageRepository beverageRepository;
    @Mock
    private BeverageConverter beverageConverter;
    @InjectMocks
    private BeverageService beverageService;

    @BeforeEach
    public void beforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBeverages_returnListBeverageDto() {
        //Given
        Beverage beverage1 = new Beverage(1L,"Any","Any");
        Beverage beverage2 = new Beverage(2L,"Any","Any");

        BeverageDto beverageDto1 = new BeverageDto(1L,"Any","Any");
        BeverageDto beverageDto2 = new BeverageDto(2L,"Any","Any");

        List<BeverageDto> expected = List.of(beverageDto1, beverageDto2);

        when(beverageConverter.beverageToBeverageDto(beverage1)).thenReturn(beverageDto1);
        when(beverageConverter.beverageToBeverageDto(beverage2)).thenReturn(beverageDto2);
        when(beverageRepository.findAll()).thenReturn(List.of(beverage1, beverage2));

        //When
        List<BeverageDto> actual = beverageService.getAllBeverages();

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getBeverageById_returnBeverageDtoIfBeverageRepositoryHasBeverage() {
        //Given
        Long beverageId = 1L;
        Beverage beverage = new Beverage(beverageId,"Any","Any");

        BeverageDto expected = new BeverageDto(beverageId,"Any","Any");

        when(beverageRepository.findById(beverageId)).thenReturn(Optional.of(beverage));
        when(beverageConverter.beverageToBeverageDto(beverage)).thenReturn(expected);

        //When
        BeverageDto actual = beverageService.getBeverageById(beverageId);

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getBeverageByName_returnBeverageDtoIfBeverageRepositoryHasBeverage() {
        //Given
        String name = "Poc";
        Beverage beverage = new Beverage(1L, name,"Any");

        BeverageDto expected = new BeverageDto(1L, name,"Any");

        when(beverageRepository.findByName(name)).thenReturn(Optional.of(beverage));
        when(beverageConverter.beverageToBeverageDto(beverage)).thenReturn(expected);

        //When
        BeverageDto actual = beverageService.getBeverageByName(name);

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void createBeverage_returnSavedBeverageDto() {
        //Given
        BeverageDto beverageDto = new BeverageDto(1L, "Any", "Any");

        Beverage beverage = new Beverage(1L, "Any", "Any");

        when(beverageConverter.beverageDtoToBeverage(beverageDto)).thenReturn(beverage);
        when(beverageConverter.beverageToBeverageDto(beverage)).thenReturn(beverageDto);

        //When
        BeverageDto actual = beverageService.createBeverage(beverageDto);

        //Then
        verify(beverageRepository, times(1)).save(beverage);
        assertThat(actual).isEqualTo(beverageDto);
    }

    @Test
    void updateBeverage_returnUpdatedBeverageDto() {
        //Given
        Long beverageId = 1L;
        Beverage existedBeverage = new Beverage(beverageId, "Cola", "Non-alcoholic");

        BeverageDto beverageDtoToUpdate = new BeverageDto(beverageId, "Shmurdyak", "Unknown");

        BeverageDto expected = new BeverageDto(beverageId, "Shmurdyak", "Unknown");

        when(beverageRepository.findById(beverageId)).thenReturn(Optional.of(existedBeverage));
        when(beverageConverter.beverageToBeverageDto(existedBeverage)).thenReturn(expected);

        //When
        BeverageDto actual = beverageService.updateBeverage(beverageDtoToUpdate);

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateBeverage_returnBeverageDtoWithUpdatedName() {
        //Given
        Long beverageId = 1L;
        String name = "Shmurdyak";
        Beverage existedBeverage = new Beverage(beverageId, "Cola", "Non-alcoholic");

        BeverageDto beverageDtoToUpdate = new BeverageDto(beverageId, name, null);

        BeverageDto expected = new BeverageDto(beverageId, name, "Non-alcoholic");

        when(beverageRepository.findById(beverageId)).thenReturn(Optional.of(existedBeverage));
        when(beverageConverter.beverageToBeverageDto(existedBeverage)).thenReturn(expected);

        //When
        BeverageDto actual = beverageService.updateBeverage(beverageDtoToUpdate);

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateBeverage_returnBeverageDtoWithUpdatedDescription() {
        //Given
        Long beverageId = 1L;
        String description = "Unknown";
        Beverage existedBeverage = new Beverage(beverageId, "Cola", "Non-alcoholic");

        BeverageDto beverageDtoToUpdate = new BeverageDto(beverageId, null, description);

        BeverageDto expected = new BeverageDto(beverageId, "Cola", description);

        when(beverageRepository.findById(beverageId)).thenReturn(Optional.of(existedBeverage));
        when(beverageConverter.beverageToBeverageDto(existedBeverage)).thenReturn(expected);

        //When
        BeverageDto actual = beverageService.updateBeverage(beverageDtoToUpdate);

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteBeverage_checkThatBeverageWasDelete() {
        //Given
        Long beverageId = 1L;

        //When
        beverageService.deleteBeverage(beverageId);

        //Then
        verify(beverageRepository, times(1)).removeMeetingsBeverages(beverageId);
        verify(beverageRepository, times(1)).removeUsersBeverages(beverageId);
        verify(beverageRepository, times(1)).removeBeverage(beverageId);
    }

    @Test
    void getBeverageEntitiesByIds_returnListBeverage() {
        //Given
        Long beverageId1 = 1L;
        Long beverageId2 = 2L;
        List<Long> beverageIdToFind = List.of(beverageId1, beverageId2);

        Beverage beverage1 = new Beverage(beverageId1,"Any","Any");
        Beverage beverage2 = new Beverage(beverageId2,"Any","Any");
        List<Beverage> expected = List.of(beverage1, beverage2);

        when(beverageRepository.findAllById(beverageIdToFind)).thenReturn(expected);

        //When
        List<Beverage> actual = beverageService.getBeverageEntitiesByIds(beverageIdToFind);

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getBeverageEntityById_returnBeverageIfBeverageRepositoryHasBeverage() {
        //Given
        Long beverageId = 1L;
        Beverage expected = new Beverage(beverageId,"Any","Any");

        when(beverageRepository.findById(beverageId)).thenReturn(Optional.of(expected));

        //When
        Beverage actual = beverageService.getBeverageEntityById(beverageId);

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getBeverageEntityById_throwEntityNotFoundExceptionIfBeverageRepositoryHasNoBeverage() {
        //Given
        Long beverageId = 1L;
        String exceptionMessage = "The beverage does not exist by Id: " + beverageId;

        when(beverageRepository.findById(beverageId)).thenReturn(Optional.empty());

        //When and Then
        assertThatThrownBy(()-> beverageService.getBeverageEntityById(beverageId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void getBeverageEntityByName_returnBeverageIfBeverageRepositoryHasBeverage() {
        //Given
        String beverageName = "Shmurdyak";
        Beverage expected = new Beverage(1L,beverageName,"Any");

        when(beverageRepository.findByName(beverageName)).thenReturn(Optional.of(expected));

        //When
        Beverage actual = beverageService.getBeverageEntityByName(beverageName);

        //Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getBeverageEntityByName_throwEntityNotFoundExceptionIfBeverageRepositoryHasNoBeverage() {
        //Given
        String beverageName = "Shmurdyak";
        String exceptionMessage = "The beverage does not exist by Id: " + beverageName;

        when(beverageRepository.findByName(beverageName)).thenReturn(Optional.empty());

        //When and Then
        assertThatThrownBy(()-> beverageService.getBeverageEntityByName(beverageName))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(exceptionMessage);
    }
}
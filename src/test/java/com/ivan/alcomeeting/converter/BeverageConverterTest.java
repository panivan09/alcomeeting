package com.ivan.alcomeeting.converter;

import com.ivan.alcomeeting.dto.BeverageDto;
import com.ivan.alcomeeting.entity.Beverage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

class BeverageConverterTest {
    @InjectMocks
    private BeverageConverter beverageConverter;

    @BeforeEach
    public void beforeTests(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void beverageToBeverageDto_returnBeverageDtoAfterConverter() {
        // Given
        Beverage beverage = new Beverage(1L, "Shmurdyak", "Unknown");
        BeverageDto expected = new BeverageDto(1L, "Shmurdyak", "Unknown");

        // When
        BeverageDto actual = beverageConverter.beverageToBeverageDto(beverage);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void beverageDtoToBeverage_returnBeverageAfterConverter() {
        // Given
        BeverageDto beverageDto = new BeverageDto(1L, "Shmurdyak", "Unknown");
        Beverage expected = new Beverage(1L, "Shmurdyak", "Unknown");

        // When
        Beverage actual = beverageConverter.beverageDtoToBeverage(beverageDto);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
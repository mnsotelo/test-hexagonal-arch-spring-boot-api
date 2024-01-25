package com.example.interviewgft.prices.application;

import com.example.interviewgft.prices.application.exception.InputDateException;
import com.example.interviewgft.prices.application.port.in.PriceQueryInput;
import com.example.interviewgft.prices.application.service.PriceQueryService;
import com.example.interviewgft.prices.domain.Price;
import com.example.interviewgft.prices.domain.exception.PriceNotFoundException;
import com.example.interviewgft.prices.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PriceQueryServiceTest {
    private PriceRepository priceRepository;
    private PriceQueryService priceQueryService;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        priceQueryService = new PriceQueryService(priceRepository);
    }

    @Test
    void whenInputIsValid_thenReturnsPrice() throws PriceNotFoundException, InputDateException {
        var input = new PriceQueryInput("2020-06-14-10.00.00", 35455, 1);
        when(priceRepository.findPriceByCriteria(anyInt(), anyInt(), any(LocalDateTime.class))).thenReturn(List.of(getPrice()));

        assertDoesNotThrow(() -> priceQueryService.execute(input));
    }

    @Test
    void whenDateIsInvalid_thenThrowsInputDateException() {
        var input = new PriceQueryInput("invalid-date", 35455, 1);

        assertThrows(InputDateException.class, () -> priceQueryService.execute(input));
    }

    @Test
    void whenPriceNotFound_thenThrowsPriceNotFoundException() throws PriceNotFoundException {
        var input = new PriceQueryInput("2020-06-14-10.00.00", 35455, 1);
        when(priceRepository.findPriceByCriteria(anyInt(), anyInt(), any(LocalDateTime.class))).thenThrow(new PriceNotFoundException("No prices found"));

        assertThrows(PriceNotFoundException.class, () -> priceQueryService.execute(input));
    }

    private Price getPrice() {
        return new Price(1,
                "2020-06-14-10.00.00",
                "2020-06-14-15.00.00",
                1, 1, 1, 999,
                "EUR");
    }
}

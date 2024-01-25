package com.example.interviewgft.prices.domain;

import com.example.interviewgft.prices.domain.exception.PriceNotFoundException;
import com.example.interviewgft.prices.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PriceQueryDisambiguationServiceTest {
    private PriceRepository priceRepository;
    private PriceQueryDisambiguationService service;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        service = new PriceQueryDisambiguationService(priceRepository);
    }

    @Test
    void whenSinglePriceFound_thenReturnPrice() throws PriceNotFoundException {
        when(priceRepository.findPriceByCriteria(anyInt(), anyInt(), any(LocalDateTime.class)))
                .thenReturn(List.of(getPrice()));

        Price result = service.execute(LocalDateTime.now(), 1, 1);

        assertEquals(getPrice().cost(), result.cost());
    }

    @Test
    void whenMultiplePricesFound_thenReturnHighestPriorityPrice() throws PriceNotFoundException {
        when(priceRepository.findPriceByCriteria(anyInt(), anyInt(), any(LocalDateTime.class)))
                .thenReturn(List.of(getPrice(), getPriceWithHigherPriority()));

        Price result = service.execute(LocalDateTime.now(), 1, 1);

        assertEquals(getPriceWithHigherPriority().cost(), result.cost());
    }

    @Test
    void whenNoPricesFound_thenThrowPriceNotFoundException() throws PriceNotFoundException {
        when(priceRepository.findPriceByCriteria(anyInt(), anyInt(), any(LocalDateTime.class)))
                .thenThrow(new PriceNotFoundException(""));

        assertThrows(PriceNotFoundException.class, () -> service.execute(LocalDateTime.now(), 1, 1));
    }

    private Price getPrice() {
        return new Price(1,
                "2020-06-14-10.00.00",
                "2020-06-14-15.00.00",
                1, 1, 1, 999,
                "EUR");
    }

    private Price getPriceWithHigherPriority() {
        return new Price(1,
                "2020-06-14-10.00.00",
                "2020-06-14-15.00.00",
                1, 1, 2, 100,
                "EUR");
    }
}


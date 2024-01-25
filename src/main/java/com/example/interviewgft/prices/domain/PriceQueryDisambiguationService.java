package com.example.interviewgft.prices.domain;

import com.example.interviewgft.prices.domain.exception.PriceNotFoundException;
import com.example.interviewgft.prices.domain.repository.PriceRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class PriceQueryDisambiguationService {
    private final PriceRepository priceRepository;

    public PriceQueryDisambiguationService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price execute(LocalDateTime date, int productId, int brandId) throws PriceNotFoundException {

        var priceList = priceRepository.findPriceByCriteria(productId, brandId, date);

        if (priceList.size() > 1) {
            return findHighestPriorityPrice(priceList);
        }

        return priceList.get(0);
    }

    private Price findHighestPriorityPrice(List<Price> priceList) {
        // Filter prices that are applicable for the given date
        // Sort the prices by priority in descending order
        // Return the price with the highest priority if any
        return priceList.stream()
                .max(Comparator.comparingInt(Price::priority))
                .get();
    }
}

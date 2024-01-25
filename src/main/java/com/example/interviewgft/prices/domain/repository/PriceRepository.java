package com.example.interviewgft.prices.domain.repository;

import com.example.interviewgft.prices.domain.Price;
import com.example.interviewgft.prices.domain.exception.PriceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {
    List<Price> findPriceByCriteria(int productId, int brandId, LocalDateTime date) throws PriceNotFoundException;
}

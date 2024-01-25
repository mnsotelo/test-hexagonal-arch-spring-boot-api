package com.example.interviewgft.prices.infrastructure.repository;

import com.example.interviewgft.prices.domain.Price;
import com.example.interviewgft.prices.domain.exception.PriceNotFoundException;
import com.example.interviewgft.prices.domain.repository.PriceRepository;
import com.example.interviewgft.prices.infrastructure.driven.persistence.JpaPriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriceRepositoryImpl implements PriceRepository {

    private final JpaPriceRepository jpaPriceRepository;

    public PriceRepositoryImpl(JpaPriceRepository jpaPriceRepository) {
        this.jpaPriceRepository = jpaPriceRepository;
    }

    @Override
    public List<Price> findPriceByCriteria(int productId, int brandId, LocalDateTime date) throws PriceNotFoundException {

        var pricesFound = jpaPriceRepository.findByProductIdAndBrandIdAndDate(productId, brandId, date);

        if (pricesFound == null || pricesFound.isEmpty()) {
            throw new PriceNotFoundException("No prices have been found");
        }

        return pricesFound.stream()
                .map(priceEntity -> new Price(
                        priceEntity.getBrandId(),
                        priceEntity.getStartDate().toString(),
                        priceEntity.getEndDate().toString(),
                        priceEntity.getPriceList(),
                        priceEntity.getProductId(),
                        priceEntity.getPriority(),
                        priceEntity.getPrice(),
                        priceEntity.getCurrency()
                ))
                .toList();
    }

}

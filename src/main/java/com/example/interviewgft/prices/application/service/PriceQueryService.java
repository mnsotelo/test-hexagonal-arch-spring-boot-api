package com.example.interviewgft.prices.application.service;

import com.example.interviewgft.prices.application.exception.InputDateException;
import com.example.interviewgft.prices.application.port.PriceQueryUseCase;
import com.example.interviewgft.prices.application.port.in.PriceQueryInput;
import com.example.interviewgft.prices.domain.Price;
import com.example.interviewgft.prices.domain.PriceQueryDisambiguationService;
import com.example.interviewgft.prices.domain.exception.PriceNotFoundException;
import com.example.interviewgft.prices.domain.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class PriceQueryService implements PriceQueryUseCase<PriceQueryInput, Price> {
    private final PriceRepository priceRepository;

    public PriceQueryService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price execute(PriceQueryInput priceQueryInput) throws PriceNotFoundException, InputDateException {
        var domainService = new PriceQueryDisambiguationService(priceRepository);
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");

        try {
            var dateTime = LocalDateTime.parse(priceQueryInput.date(), formatter);
            return domainService.execute(dateTime, priceQueryInput.productId(), priceQueryInput.brandId());
        } catch (DateTimeParseException exception) {
            throw new InputDateException("""
                    There was a problem with your date format,
                    "please follow the following pattern: yyyy-MM-dd-HH.mm.ss""");
        }
    }
}

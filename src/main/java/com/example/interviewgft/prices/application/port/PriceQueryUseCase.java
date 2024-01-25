package com.example.interviewgft.prices.application.port;

import com.example.interviewgft.prices.application.exception.InputDateException;
import com.example.interviewgft.prices.domain.exception.PriceNotFoundException;

public interface PriceQueryUseCase<Query, Result> {
    Result execute(Query query) throws PriceNotFoundException, InputDateException;
}

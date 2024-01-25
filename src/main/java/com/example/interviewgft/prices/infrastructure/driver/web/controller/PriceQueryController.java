package com.example.interviewgft.prices.infrastructure.driver.web.controller;

import com.example.interviewgft.prices.application.exception.InputDateException;
import com.example.interviewgft.prices.application.port.PriceQueryUseCase;
import com.example.interviewgft.prices.application.port.in.PriceQueryInput;
import com.example.interviewgft.prices.domain.Price;
import com.example.interviewgft.prices.domain.exception.PriceNotFoundException;
import com.example.interviewgft.prices.infrastructure.driver.web.api.PriceQueryControllerApi;
import com.example.interviewgft.prices.infrastructure.driver.web.api.PriceQueryRequest;
import com.example.interviewgft.prices.infrastructure.driver.web.presenter.PriceQueryPresenter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceQueryController implements PriceQueryControllerApi {

    private final PriceQueryUseCase<PriceQueryInput, Price> priceQueryUseCase;


    public PriceQueryController(PriceQueryUseCase<PriceQueryInput, Price> priceQueryUseCase) {
        this.priceQueryUseCase = priceQueryUseCase;
    }

    @Override
    @GetMapping("/price")
    public ResponseEntity<Object> getPrice(PriceQueryRequest priceQueryRequest) {
        PriceQueryInput priceQueryInput = new PriceQueryInput(priceQueryRequest.date(), priceQueryRequest.productId(), priceQueryRequest.brandId());
        try {
            var price = priceQueryUseCase.execute(priceQueryInput);
            return new ResponseEntity<>(PriceQueryPresenter.from(price), HttpStatus.OK);
        } catch (PriceNotFoundException e) {
            return new ResponseEntity<>("no prices have been found", HttpStatus.NO_CONTENT);
        } catch (InputDateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

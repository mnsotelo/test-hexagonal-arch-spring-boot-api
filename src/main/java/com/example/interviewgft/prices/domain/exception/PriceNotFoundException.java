package com.example.interviewgft.prices.domain.exception;

public class PriceNotFoundException extends Exception {
    private final String errorMessage;

    public PriceNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
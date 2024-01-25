package com.example.interviewgft.prices.application.exception;

public class InputDateException extends Exception {

    private final String errorMessage;

    public InputDateException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage(){
        return errorMessage;
    }
}
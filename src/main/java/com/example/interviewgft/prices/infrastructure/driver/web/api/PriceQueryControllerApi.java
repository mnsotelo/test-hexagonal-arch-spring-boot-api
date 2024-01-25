package com.example.interviewgft.prices.infrastructure.driver.web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface PriceQueryControllerApi {

    @Operation(summary = "Get the price", description = "Returns a price using the date, product identifier and brand id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price found"),
            @ApiResponse(responseCode = "204", description = "No price found"),
            @ApiResponse(responseCode = "400", description = "Bad request - The date format is not correct")
    })
    public ResponseEntity<Object> getPrice(PriceQueryRequest priceQueryRequest);
}

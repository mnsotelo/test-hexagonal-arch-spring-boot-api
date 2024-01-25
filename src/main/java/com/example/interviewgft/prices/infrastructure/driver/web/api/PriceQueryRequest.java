package com.example.interviewgft.prices.infrastructure.driver.web.api;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestParam;

public record PriceQueryRequest(
        @RequestParam
        @Schema(name = "Date", example = "2023-01-01-10.00.00")
        String date,
        @RequestParam
        @Schema(name = "Product identifier", example = "1")
        int productId,
        @RequestParam
        @Schema(name = "Brand identifier", example = "1")
        int brandId
) {
}

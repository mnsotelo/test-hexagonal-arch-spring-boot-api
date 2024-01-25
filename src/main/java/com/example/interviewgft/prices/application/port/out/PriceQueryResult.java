package com.example.interviewgft.prices.application.port.out;

import java.time.LocalDateTime;

public record PriceQueryResult(int productId, int brandId, int priceList, LocalDateTime startDate,
                               LocalDateTime endDate, long price) {
}

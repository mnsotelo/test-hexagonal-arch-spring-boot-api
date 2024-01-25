package com.example.interviewgft.prices.domain;

public record Price(int brandId, String startDate, String endDate,
                    int priceList, int productId, int priority, double cost, String currency) {
}

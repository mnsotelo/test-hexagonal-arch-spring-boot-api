package com.example.interviewgft.prices.infrastructure.driver.web.presenter;

import com.example.interviewgft.prices.domain.Price;

public record PriceQueryPresenter(
        int productId,
        int brandId,
        int priceList,
        String startDate,
        String endDate,
        String currency,
        double price) {
    public static PriceQueryPresenter from(Price price) {
        return new PriceQueryPresenter(price.productId(),
                price.brandId(), price.priceList(), price.startDate(),
                price.endDate(), price.currency(), price.cost());
    }
}

package com.goods.product.task1;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final PriceRepository priceRepository;

    public DataInitializer(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @PostConstruct
    public void populateDatabase() {
        List<Price> prices = new ArrayList<>();
        for (int i = 0; i < 13000; i++) {
            Price price = new Price();
            price.setValue(Math.random() * 100);
            prices.add(price);

            // Сохраняем пачками по 1000 записей для улучшения производительности
            if (prices.size() % 13000 == 0) {
                priceRepository.saveAll(prices);
                prices.clear();
                break;
            }
        }
        if (!prices.isEmpty()) {
            priceRepository.saveAll(prices);
        }
    }
}

package com.goods.product.task1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class SchedulerConfiguration {

    private final PriceRepository priceRepository;
    private final boolean schedulingEnabled;
    private final boolean optimizationEnabled;

    public SchedulerConfiguration(
            PriceRepository priceRepository,
            @Value("${app.scheduling.enabled:false}") boolean schedulingEnabled,
            @Value("${scheduling.optimization:false}") boolean optimizationEnabled
    ) {
        this.priceRepository = priceRepository;
        this.schedulingEnabled = schedulingEnabled;
        this.optimizationEnabled = optimizationEnabled;
    }

    @Scheduled(fixedRate = 60000) // запуск раз в минуту
    @Transactional
    public void schedulePriceUpdate() throws IOException {
        if (!schedulingEnabled) {
            return; // Шедулер отключен
        }

        if (optimizationEnabled) {
            // Оптимизированный шедулер
            updatePricesOptimized();
        } else {
            // Простой шедулер
            updatePricesSimple();
        }
    }

    private void updatePricesSimple() {
        System.out.println("Запуск простого шедулера...");
        List<Price> prices = priceRepository.findAll(); // Загрузить все записи
        prices.forEach(price -> price.setValue(price.getValue() * 1.1)); // Изменить цену
        priceRepository.saveAll(prices); // Сохранить изменения
    }

    private void updatePricesOptimized() throws IOException {
        System.out.println("Запуск оптимизированного шедулера...");
        writePricesToFile();
        updatePricesWithBatch();
    }

    private void updatePricesWithBatch() {
        int batchSize = 1000;
        int page = 0;
        while (true) {
            var prices = priceRepository.findAll(PageRequest.of(page, batchSize));
            if (prices.isEmpty()) break;

            prices.forEach(price -> price.setValue(price.getValue() * 1.1));
            priceRepository.saveAll(prices.getContent());

            page++;
        }
    }

    private void writePricesToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("prices.txt"))) {
            priceRepository.findAll()
                    .forEach(price -> {
                        try {
                            writer.write(price.toString());
                            writer.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }
}

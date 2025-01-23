package com.goods.product.task1;
import com.goods.product.model.Product;
import com.goods.product.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

@Component
public class PriceScheduler {

    @Value("${app.scheduling.enabled}")
    private boolean isSchedulingEnabled;

    @Value("${scheduling.optimization}")
    private boolean isOptimizationEnabled;

    @Autowired
    private ProductRepository productRepository; // Репозиторий для работы с продуктами

    @Scheduled(fixedRate = 300000) // Каждую минуту
    public void updatePricesSimple() {
        System.out.println("Простой шедулер: Проверяем условия запуска.");
        if (isSchedulingEnabled && !isOptimizationEnabled) {
            System.out.println("Простой шедулер запущен");

            // Код обновления цен
            Iterable<Product> products = productRepository.findAll();
            for (Product product : products) {
                product.setPrice(product.getPrice().multiply(new BigDecimal("1.05"))); // Увеличиваем цену на 5%
                productRepository.save(product);
            }

            System.out.println("Простой шедулер завершил работу");
        } else {
            System.out.println("Простой шедулер не запущен, так как условия не выполнены");
        }
    }

    @Scheduled(fixedRate = 60000) // Каждую минуту
    public void updatePricesOptimized() {
        System.out.println("Оптимизированный шедулер: Проверяем условия запуска.");
        if (isSchedulingEnabled && isOptimizationEnabled) {
            System.out.println("Оптимизированный шедулер запущен");

            // Код обновления цен
            Iterable<Product> products = productRepository.findAll();
            for (Product product : products) {
                // Можете добавить более сложную логику изменения цен
                product.setPrice(product.getPrice().multiply(new BigDecimal("1.05"))); // Увеличиваем цену на 10%
                productRepository.save(product);
            }

            System.out.println("Оптимизированный шедулер завершил работу");
        } else {
            System.out.println("Оптимизированный шедулер не запущен, так как условия не выполнены");
        }
    }
}

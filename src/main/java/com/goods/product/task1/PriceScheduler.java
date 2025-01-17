package com.goods.product.task1;

import com.goods.product.model.Product;
import com.goods.product.repository.ProductRepository;
import com.goods.product.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PriceScheduler {

    @Autowired
    private ProductService productService;

    // Простой шедулер
    @Scheduled(cron = "0 0 * * * ?") // every hour
    public void updatePricesSimple() {
        productService.updateProductPrices();
    }

    // Оптимизированный шедулер (в примере не оптимизированный, для демонстрации)
    @Scheduled(cron = "0 0 * * * ?")
    public void updatePricesOptimized() {
        productService.updateProductPrices();
    }
}

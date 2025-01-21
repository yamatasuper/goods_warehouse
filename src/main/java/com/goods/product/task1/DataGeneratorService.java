package com.goods.product.task1;

import com.goods.product.model.Product;
import com.goods.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
public class DataGeneratorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void generateBatch(int start, int batchSize) {
        try {
            List<Object[]> batchArgs = new ArrayList<>();
            for (int i = start; i < start + batchSize; i++) {
                batchArgs.add(new Object[]{
                        "Category" + (i % 5),
                        "2025-01-01",
                        "Description for product " + i,
                        "2025-01-14",
                        "Product " + i,
                        BigDecimal.valueOf(100 + (i % 100)),
                        10 + (i % 50),
                        "SKU" + i
                });
            }

            String sql = "INSERT INTO product (category, created_at, description, last_quantity_update, name, price, quantity, sku) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.batchUpdate(sql, batchArgs);
        } catch (Exception e) {
            throw new RuntimeException("Error generating batch: " + e.getMessage(), e);
        }
    }
}

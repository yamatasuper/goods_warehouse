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
        List<Object[]> batchArgs = new ArrayList<>();
        for (int i = start; i < start + batchSize; i++) {
            batchArgs.add(new Object[]{
                    "Product " + i,
                    "SKU" + i,
                    "Description for product " + i,
                    "Category" + (i % 5),
                    BigDecimal.valueOf(100 + (i % 100)),
                    10 + (i % 50),
                    "2025-01-14", // Last quantity update
                    "2025-01-01"  // Created at
            });
        }

        String sql = "INSERT INTO product (name, sku, description, category, price, quantity, last_quantity_update, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}

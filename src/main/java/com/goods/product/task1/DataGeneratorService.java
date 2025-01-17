package com.goods.product.task1;

import com.goods.product.model.Product;
import com.goods.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataGeneratorService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void generateData(int count) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Product product = new Product();
            product.setName("Product " + i);
            product.setSku("SKU" + i);
            product.setDescription("Description for product " + i);
            product.setCategory("Category" + (i % 5)); // Example category
            product.setPrice(BigDecimal.valueOf(100 + (i % 100))); // Random price
            product.setQuantity(10 + (i % 50)); // Random quantity
            product.setLastQuantityUpdate("2025-01-14");
            product.setCreatedAt("2025-01-01");
            products.add(product);

            // Batch insert every 1000 records
            if (products.size() >= 1000) {
                productRepository.saveAll(products);
                products.clear();
            }
        }

        // Insert remaining records
        if (!products.isEmpty()) {
            productRepository.saveAll(products);
        }
    }
}

package com.goods.product.service;

import com.goods.product.model.Product;
import com.goods.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import jakarta.transaction.Transactional;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateProductPrices() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> {
            product.setPrice(product.getPrice().multiply(BigDecimal.valueOf(1.1))); // Example price increase
            productRepository.save(product);
        });
    }
}
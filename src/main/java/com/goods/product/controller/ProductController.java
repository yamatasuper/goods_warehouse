package com.goods.product.controller;

import com.goods.product.model.Product;
import com.goods.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

//    @PostMapping
//    public Product createProduct(@RequestBody Product product) {
//        return productService.saveProduct(product);
//    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // Дополнительные эндпоинты по необходимости
}

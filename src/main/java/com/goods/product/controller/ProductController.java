package com.goods.product.controller;

import com.goods.product.model.Product;
import com.goods.product.service.ProductService;
import com.goods.product.task3.microservice.CurrencyProvider;
import com.goods.product.task3.model.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
  @Autowired private ProductService productService;
  @Autowired private CurrencyProvider currencyProvider; // для получения валюты из сессии

  @GetMapping("/")
  public List<ProductResponse> getAllProducts() {
    String currency = currencyProvider.getCurrency();
    List<Product> products = productService.getAllProducts();
    return products.stream()
        .map(product -> new ProductResponse(product, currency)) // добавляем валюту в ответ
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ProductResponse getProductById(@PathVariable Long id) {
    String currency = currencyProvider.getCurrency();
    Product product = productService.getProductById(id);
    return new ProductResponse(product, currency);
  }
}

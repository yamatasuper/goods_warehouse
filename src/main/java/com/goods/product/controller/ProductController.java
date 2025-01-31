package com.goods.product.controller;

import com.goods.product.model.Product;
import com.goods.product.service.ProductService;
import com.goods.product.task3.microservice.CurrencyConversionService;
import com.goods.product.task3.microservice.CurrencyProvider;
import com.goods.product.task3.model.ProductResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired private ProductService productService;

  @Autowired private CurrencyProvider currencyProvider; // для получения валюты из сессии

  @Autowired
  private CurrencyConversionService currencyConversionService; // сервис для конвертации цены

  @GetMapping("/")
  public List<ProductResponse> getAllProducts() {
    String currency = currencyProvider.getCurrency();
    List<Product> products = productService.getAllProducts();

    // Преобразуем каждый продукт и пересчитываем цену в зависимости от валюты
    return products.stream()
        .map(
            product -> {
              BigDecimal convertedPrice =
                  currencyConversionService.convertPrice(product.getPrice(), currency);
              product.setPrice(convertedPrice); // изменяем цену на пересчитанную
              return new ProductResponse(product, currency); // возвращаем ответ с новой ценой
            })
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ProductResponse getProductById(@PathVariable Long id) {
    String currency = currencyProvider.getCurrency();
    Product product = productService.getProductById(id);

    // Пересчитываем цену в зависимости от валюты
    BigDecimal convertedPrice =
        currencyConversionService.convertPrice(product.getPrice(), currency);
    product.setPrice(convertedPrice); // изменяем цену на пересчитанную

    return new ProductResponse(product, currency); // возвращаем ответ с новой ценой
  }
}

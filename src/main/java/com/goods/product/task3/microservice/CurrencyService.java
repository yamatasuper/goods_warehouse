package com.goods.product.task3.microservice;

import java.math.BigDecimal;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currencies")
public class CurrencyService {
  @GetMapping
  public ResponseEntity<Map<String, BigDecimal>> getExchangeRates() {
    if (Math.random() < 0.5) {
      throw new RuntimeException("Service error currencies"); // генерируем ошибку 50% времени
    }
    Map<String, BigDecimal> rates =
        Map.of(
            "USD", new BigDecimal("98.5"),
            "EUR", new BigDecimal("102.3"),
            "CNY", new BigDecimal("12.86"));
    return ResponseEntity.ok(rates);
  }
}

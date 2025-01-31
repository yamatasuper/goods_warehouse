package com.goods.product.task1.service;

import com.goods.product.task3.microservice.ExchangeRateService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataGeneratorService {

  @Autowired private JdbcTemplate jdbcTemplate;

  private final ExchangeRateService exchangeRateService;

  // Конструктор для внедрения зависимости
  @Autowired
  public DataGeneratorService(ExchangeRateService exchangeRateService) {
    this.exchangeRateService = exchangeRateService;
  }

  public void generateBatch(int start, int batchSize, String currency) {
    List<Object[]> batchArgs = new ArrayList<>();

    // Берем курс для выбранной валюты
    BigDecimal rate = exchangeRateService.getExchangeRate(currency);

    // Если курс не найден, оставляем цену в RUB
    if (rate == null) {
      System.out.println("Курс для валюты " + currency + " не найден. Оставляем цену в RUB.");
      rate = BigDecimal.valueOf(1); // Используем 1 как курс для RUB
    }

    for (int i = start; i < start + batchSize; i++) {
      BigDecimal priceInRub = BigDecimal.valueOf(100 + (i % 100));
      // Переводим цену в нужную валюту
      BigDecimal convertedPrice = priceInRub.divide(rate, 2, RoundingMode.HALF_UP);

      batchArgs.add(
          new Object[] {
            "Product " + i,
            "SKU" + i,
            "Description for product " + i,
            "Category" + (i % 5),
            convertedPrice,
            10 + (i % 50),
            "2025-01-14",
            "2025-01-01",
            currency,
            0
          });
    }

    String sql =
        "INSERT INTO product (name, sku, description, category, price, quantity, last_quantity_update, created_at, currency, version) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    jdbcTemplate.batchUpdate(sql, batchArgs);
  }
}

package com.goods.product.task3.microservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CurrencyServiceClient {
  @Autowired private WebClient.Builder webClientBuilder;

  public Map<String, BigDecimal> getExchangeRates() {
    try {
      return webClientBuilder
          .baseUrl("http://localhost:8081")
          .build()
          .get()
          .uri("/api/v1/currencies")
          .retrieve()
          .bodyToMono(Map.class)
          .block();
    } catch (Exception e) {
      return loadExchangeRatesFromFile(); // если ошибка, читаем из файла
    }
  }

  private Map<String, BigDecimal> loadExchangeRatesFromFile() {
    try (InputStream is = getClass().getResourceAsStream("/exchange-rate.json")) {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(is, Map.class);
    } catch (IOException e) {
      throw new RuntimeException("Error reading exchange rates from file", e);
    }
  }
}

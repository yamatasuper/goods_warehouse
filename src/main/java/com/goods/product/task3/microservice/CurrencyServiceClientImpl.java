package com.goods.product.task3.microservice;

import java.math.BigDecimal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CurrencyServiceClientImpl implements CurrencyServiceClientInterface {
  @Autowired private WebClient.Builder webClientBuilder;

  @Override
  public Map<String, BigDecimal> getExchangeRates() {
    return webClientBuilder
        .baseUrl("http://localhost:8081")
        .build()
        .get()
        .uri("/api/v1/currencies")
        .retrieve()
        .bodyToMono(Map.class)
        .block();
  }
}

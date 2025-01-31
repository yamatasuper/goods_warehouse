package com.goods.product.task3.microservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRateService {
  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  @Value("${currency-service.host}${currency-service.methods.get-currency}")
  private String currencyServiceUrl;

  public ExchangeRateService(
      RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper, Environment environment) {
    this.restTemplate = restTemplateBuilder.build();
    this.objectMapper = objectMapper;
  }

  public BigDecimal getExchangeRate(String currency) {
    try {
      ResponseEntity<Map<String, BigDecimal>> response =
          restTemplate.exchange(
              currencyServiceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
      return response.getBody().getOrDefault(currency, BigDecimal.ONE);
    } catch (Exception e) {
      return getExchangeRateFromFile(currency);
    }
  }

  private BigDecimal getExchangeRateFromFile(String currency) {
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("exchange-rate.json")) {
      if (is == null) return BigDecimal.ONE;
      Map<String, BigDecimal> rates =
          objectMapper.readValue(is, new TypeReference<Map<String, BigDecimal>>() {});
      return rates.getOrDefault("exchangeRate" + currency.toUpperCase(), BigDecimal.ONE);
    } catch (IOException e) {
      return BigDecimal.ONE;
    }
  }
}

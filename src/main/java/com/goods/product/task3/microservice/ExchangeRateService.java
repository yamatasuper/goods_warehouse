package com.goods.product.task3.microservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@EnableCaching
public class ExchangeRateService {
  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  @Value("${currency-service.host}${currency-service.methods.get-currency}")
  private String currencyServiceUrl;

  public ExchangeRateService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
    this.restTemplate = restTemplateBuilder.build();
    this.objectMapper = objectMapper;
  }

  @Cacheable(value = "exchangeRates", key = "#currency", unless = "#result == null")
  public BigDecimal getExchangeRate(String currency) {
    try {
      ResponseEntity<Map<String, BigDecimal>> response =
          restTemplate.exchange(
              currencyServiceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
      BigDecimal rate = response.getBody().get(currency);
      if (rate != null) {
        cacheExchangeRate(currency, rate);
      }
      return rate != null ? rate : getExchangeRateFromFile(currency);
    } catch (Exception e) {
      return getExchangeRateFromFile(currency);
    }
  }

  @CachePut(value = "exchangeRates", key = "#currency")
  public BigDecimal cacheExchangeRate(String currency, BigDecimal rate) {
    return rate;
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

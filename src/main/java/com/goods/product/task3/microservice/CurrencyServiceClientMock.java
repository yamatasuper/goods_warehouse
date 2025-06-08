package com.goods.product.task3.microservice;

import java.math.BigDecimal;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("mock")
public class CurrencyServiceClientMock implements CurrencyServiceClientInterface {
  @Override
  public Map<String, BigDecimal> getExchangeRates() {
    return Map.of(
        "USD", new BigDecimal("100.0"),
        "EUR", new BigDecimal("105.0"),
        "CNY", new BigDecimal("13.0"));
  }
}

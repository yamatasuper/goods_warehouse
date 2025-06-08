package com.goods.product.task3.microservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConversionService {

  private static final Map<String, BigDecimal> currencyRates = new HashMap<>();

  static {
    currencyRates.put("CNY", new BigDecimal("0.1286"));
    currencyRates.put("USD", new BigDecimal("0.985"));
    currencyRates.put("EUR", new BigDecimal("0.1023"));
  }

  public BigDecimal convertPrice(BigDecimal price, String currency) {
    if (currencyRates.containsKey(currency)) {
      BigDecimal rate = currencyRates.get(currency);
      return price.multiply(rate);
    }
    return price;
  }
}

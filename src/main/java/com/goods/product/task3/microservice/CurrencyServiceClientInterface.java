package com.goods.product.task3.microservice;

import java.math.BigDecimal;
import java.util.Map;

public interface CurrencyServiceClientInterface {
  Map<String, BigDecimal> getExchangeRates();
}

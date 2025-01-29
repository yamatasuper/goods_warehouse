package com.goods.product.task3.microservice;

import org.springframework.stereotype.Component;

@Component
public class CurrencyProvider {
  private String currency = "RUB"; // начальная валюта

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
}

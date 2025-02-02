package com.goods.product.task3.microservice;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CurrencyFilter extends OncePerRequestFilter {
  @Autowired private CurrencyProvider currencyProvider;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String currency = request.getHeader("currency");
    if (currency != null) {
      currencyProvider.setCurrency(currency);
    } else {
      currencyProvider.setCurrency("RUB");
    }
    filterChain.doFilter(request, response);
  }
}

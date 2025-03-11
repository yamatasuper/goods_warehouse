package com.goods.product.task8.orchestrator.service;

import com.goods.product.task8.orchestrator.model.PaymentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

  private final RestTemplate restTemplate;

  public PaymentService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public PaymentResponse processPayment(String orderId, double amount, String accountNumber) {
    String url = "http://localhost:8080/api/payment/process";
    PaymentRequest request = new PaymentRequest(orderId, amount, accountNumber);
    return restTemplate.postForObject(url, request, PaymentResponse.class);
  }

  private static class PaymentRequest {
    private String orderId;
    private double amount;
    private String accountNumber;

    public PaymentRequest(String orderId, double amount, String accountNumber) {
      this.orderId = orderId;
      this.amount = amount;
      this.accountNumber = accountNumber;
    }

    // Getters and setters
  }
}

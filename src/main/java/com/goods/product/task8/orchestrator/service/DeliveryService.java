package com.goods.product.task8.orchestrator.service;

import com.goods.product.task8.orchestrator.model.DeliveryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeliveryService {

  private final RestTemplate restTemplate;

  public DeliveryService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public DeliveryResponse registerDelivery(String deliveryAddress, String orderId) {
    String url = "http://localhost:8080/api/delivery/register";
    DeliveryRequest request = new DeliveryRequest(deliveryAddress, orderId);
    return restTemplate.postForObject(url, request, DeliveryResponse.class);
  }

  private static class DeliveryRequest {
    private String deliveryAddress;
    private String orderId;

    public DeliveryRequest(String deliveryAddress, String orderId) {
      this.deliveryAddress = deliveryAddress;
      this.orderId = orderId;
    }

    // Getters and setters
  }
}

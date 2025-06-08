package com.goods.product.task8.orchestrator.service;

import com.goods.product.task8.orchestrator.model.ContractResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ContractService {

  private final RestTemplate restTemplate;

  public ContractService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public ContractResponse registerContract(String inn, String accountNumber) {
    String url = "http://localhost:8080/api/contract/register";
    ContractRequest request = new ContractRequest(inn, accountNumber);
    return restTemplate.postForObject(url, request, ContractResponse.class);
  }

  private static class ContractRequest {
    private String inn;
    private String accountNumber;

    public ContractRequest(String inn, String accountNumber) {
      this.inn = inn;
      this.accountNumber = accountNumber;
    }

    // Getters and setters
  }
}

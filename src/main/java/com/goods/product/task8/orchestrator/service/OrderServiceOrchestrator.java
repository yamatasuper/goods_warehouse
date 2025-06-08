package com.goods.product.task8.orchestrator.service;

import com.goods.product.task8.orchestrator.model.OrderRequest;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceOrchestrator {

  @Autowired private RuntimeService runtimeService;

  public String confirmOrder(OrderRequest request) {
    Map<String, Object> variables = new HashMap<>();
    variables.put("deliveryAddress", request.getDeliveryAddress());
    variables.put("inn", request.getInn());
    variables.put("accountNumber", request.getAccountNumber());
    variables.put("amount", request.getAmount());
    variables.put("login", request.getLogin());

    String businessKey =
        runtimeService.startProcessInstanceByKey("orderProcess", variables).getBusinessKey();
    // Сохраните businessKey в базу данных
    return businessKey;
  }
}

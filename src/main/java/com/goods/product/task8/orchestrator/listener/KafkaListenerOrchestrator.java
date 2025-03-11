package com.goods.product.task8.orchestrator.listener;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerOrchestrator {

  @Autowired private RuntimeService runtimeService;

  @KafkaListener(topics = "compliance-response-topic")
  public void listenComplianceResponse(String message) {
    String[] parts = message.split(",");
    String businessKey = parts[0];
    String result = parts[1];
    runtimeService
        .createMessageCorrelation("ComplianceResponseMessage")
        .processInstanceBusinessKey(businessKey)
        .setVariable("complianceResult", result)
        .correlate();
  }
}

package com.goods.product.task8.orchestrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ComplianceService {

  @Autowired private KafkaTemplate<String, String> kafkaTemplate;

  public void sendComplianceCheck(String login, String inn, String businessKey) {
    kafkaTemplate.send("compliance-topic", login + "," + inn + "," + businessKey);
  }
}

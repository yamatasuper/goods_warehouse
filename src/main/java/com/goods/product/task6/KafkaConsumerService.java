package com.goods.product.task6;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

  @KafkaListener(topics = "test_topic", groupId = "test-group")
  public void listen(String message) {
    System.out.println("Received message: " + message);
  }
}

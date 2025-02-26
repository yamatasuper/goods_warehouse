package com.goods.product.task6;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageSender {

  private final KafkaTemplate<String, byte[]> kafkaTemplate;

  public KafkaMessageSender(KafkaTemplate<String, byte[]> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(String key, String message) {
    kafkaTemplate.send("test_topic", key, message.getBytes());
  }
}

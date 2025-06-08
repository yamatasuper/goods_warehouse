package com.goods.product.task6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

  @Autowired private KafkaProducerService kafkaProducerService;

  @PostMapping("/send")
  public String sendMessage(@RequestBody String message) {
    kafkaProducerService.sendMessage(message);
    return "Message sent: " + message;
  }
}

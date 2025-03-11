package com.goods.product.task8.orchestrator.controller;

import com.goods.product.task8.orchestrator.model.OrderRequest;
import com.goods.product.task8.orchestrator.service.OrderServiceOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderControllerOrchestrator {

  @Autowired private OrderServiceOrchestrator orderService;

  @PostMapping("/confirm")
  public String confirmOrder(@RequestBody OrderRequest request) {
    return orderService.confirmOrder(request);
  }
}

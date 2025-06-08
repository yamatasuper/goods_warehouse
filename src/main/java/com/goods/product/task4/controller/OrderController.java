package com.goods.product.task4.controller;

import com.goods.product.task4.dto.request.CreateOrderRequest;
import com.goods.product.task4.dto.request.UpdateOrderRequest;
import com.goods.product.task4.dto.response.OrderResponse;
import com.goods.product.task4.service.OrderService;
import com.goods.product.task5.orders.OrderInfo;
import com.goods.product.task6.orderEvents.OrderEventService;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
  private final OrderService orderService;
  private final OrderEventService orderEventService;

  public OrderController(OrderService orderService, OrderEventService orderEventService) {
    this.orderService = orderService;
    this.orderEventService = orderEventService;
  }

  @PostMapping
  public ResponseEntity<OrderResponse> createOrder(
      @RequestHeader("customerId") Long customerId, @RequestBody CreateOrderRequest request) {
    return ResponseEntity.ok(orderService.createOrder(customerId, request));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponse> getOrder(
      @RequestHeader("customerId") Long customerId, @PathVariable UUID orderId)
      throws AccessDeniedException {
    return ResponseEntity.ok(orderService.getOrder(orderId, customerId));
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<Void> cancelOrder(
      @RequestHeader("customerId") Long customerId, @PathVariable UUID orderId)
      throws AccessDeniedException {
    orderService.cancelOrder(orderId, customerId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{orderId}")
  public ResponseEntity<Void> updateOrder(
      @RequestHeader("customerId") Long customerId,
      @PathVariable UUID orderId,
      @RequestBody UpdateOrderRequest request)
      throws AccessDeniedException {
    orderService.updateOrder(orderId, customerId, request);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{orderId}/confirm")
  public ResponseEntity<Void> confirmOrder(
      @RequestHeader("customerId") Long customerId, @PathVariable UUID orderId)
      throws AccessDeniedException {
    orderService.confirmOrder(orderId, customerId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{orderId}/complete")
  public ResponseEntity<Void> completeOrder(
      @RequestHeader("customerId") Long customerId, @PathVariable UUID orderId)
      throws AccessDeniedException {
    orderService.completeOrder(orderId, customerId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/productOrders")
  public ResponseEntity<Map<Long, List<OrderInfo>>> getProductOrders(
      @RequestParam List<Long> productIds) {
    Map<Long, List<OrderInfo>> ordersInfo =
        orderService.getOrdersInfoForProducts(productIds).join();
    return ResponseEntity.ok(ordersInfo);
  }

  @PostMapping("/event")
  public ResponseEntity<Void> handleEvent(@RequestBody String jsonEvent) {
    try {
      orderEventService.handleEvent(jsonEvent);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}

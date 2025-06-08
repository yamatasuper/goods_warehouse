package com.goods.product.task6.orderEvents;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goods.product.task4.dto.request.CreateOrderRequest;
import com.goods.product.task4.dto.request.UpdateOrderRequest;
import com.goods.product.task4.model.OrderStatus;
import com.goods.product.task4.service.OrderService;
import kotlin.io.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class OrderEventService {

  private final OrderService orderService;
  private final ObjectMapper objectMapper;

  public OrderEventService(OrderService orderService, ObjectMapper objectMapper) {
    this.orderService = orderService;
    this.objectMapper = objectMapper;
  }

  public void handleEvent(String jsonEvent) throws Exception {
    // Парсим JSON и получаем тип события
    JsonNode rootNode = objectMapper.readTree(jsonEvent);
    String eventType = rootNode.get("event").asText();

    switch (eventType) {
      case "CREATE_ORDER":
        CreateOrderEvent createOrderEvent =
            objectMapper.treeToValue(rootNode, CreateOrderEvent.class);
        handleCreateOrder(createOrderEvent);
        break;
      case "UPDATE_ORDER":
        UpdateOrderEvent updateOrderEvent =
            objectMapper.treeToValue(rootNode, UpdateOrderEvent.class);
        handleUpdateOrder(updateOrderEvent);
        break;
      case "DELETE_ORDER":
        DeleteOrderEvent deleteOrderEvent =
            objectMapper.treeToValue(rootNode, DeleteOrderEvent.class);
        handleDeleteOrder(deleteOrderEvent);
        break;
      case "UPDATE_ORDER_STATUS":
        UpdateOrderStatusEvent updateOrderStatusEvent =
            objectMapper.treeToValue(rootNode, UpdateOrderStatusEvent.class);
        handleUpdateOrderStatus(updateOrderStatusEvent);
        break;
      default:
        throw new IllegalArgumentException("Unknown event type: " + eventType);
    }
  }

  private void handleCreateOrder(CreateOrderEvent event) {
    CreateOrderRequest request = new CreateOrderRequest();
    request.setDeliveryAddress(event.getDeliveryAddress());
    request.setProducts(event.getProducts());

    orderService.createOrder(event.getCustomerId(), request);
  }

  private void handleUpdateOrder(UpdateOrderEvent event)
      throws java.nio.file.AccessDeniedException {
    UpdateOrderRequest request = new UpdateOrderRequest();
    request.setProducts(event.getProducts());

    orderService.updateOrder(event.getOrderId(), event.getCustomerId(), request);
  }

  private void handleDeleteOrder(DeleteOrderEvent event)
      throws AccessDeniedException, java.nio.file.AccessDeniedException {
    orderService.cancelOrder(event.getOrderId(), event.getCustomerId());
  }

  private void handleUpdateOrderStatus(UpdateOrderStatusEvent event)
      throws AccessDeniedException, java.nio.file.AccessDeniedException {
    OrderStatus status = OrderStatus.valueOf(event.getStatus());
    if (status == OrderStatus.REJECTED) {
      orderService.rejectOrder(
          event.getOrderId(), null); // customerId может быть null, если статус обновляется системой
    } else {
      throw new IllegalArgumentException("Unsupported status: " + event.getStatus());
    }
  }
}

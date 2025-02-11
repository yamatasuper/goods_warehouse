package com.goods.product.task4.service;

import com.goods.product.model.Product;
import com.goods.product.repository.ProductRepository;
import com.goods.product.task4.dto.request.CreateOrderRequest;
import com.goods.product.task4.dto.request.ProductRequest;
import com.goods.product.task4.dto.request.UpdateOrderRequest;
import com.goods.product.task4.dto.response.OrderProductResponse;
import com.goods.product.task4.dto.response.OrderResponse;
import com.goods.product.task4.model.Order;
import com.goods.product.task4.model.OrderItem;
import com.goods.product.task4.model.OrderStatus;
import com.goods.product.task4.repository.OrderItemRepository;
import com.goods.product.task4.repository.OrderRepository;
import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
  private OrderRepository orderRepository;
  private ProductRepository productRepository;
  private OrderItemRepository orderItemRepository;

  @Transactional
  public OrderResponse createOrder(Long customerId, CreateOrderRequest request) {
    Order order = new Order();
    order.setCustomerId(customerId);
    order.setStatus(OrderStatus.CREATED);
    order.setDeliveryAddress(request.getDeliveryAddress());

    List<OrderItem> orderItems = new ArrayList<>();
    BigDecimal totalPrice = BigDecimal.ZERO;

    for (ProductRequest productRequest : request.getProducts()) {
      Product product = productRepository.findByIdAndLock(productRequest.getId());
      if (product == null
          || !product.getIsAvailable()
          || product.getQuantity() < productRequest.getQuantity()) {
        throw new IllegalArgumentException("Продукт недоступен или недостаточное количество");
      }

      product.setQuantity(product.getQuantity() - productRequest.getQuantity());
      productRepository.save(product);

      OrderItem orderItem = new OrderItem();
      orderItem.setOrder(order);
      orderItem.setProduct(product);
      orderItem.setQuantity(productRequest.getQuantity());
      orderItem.setPriceAtOrderTime(product.getPrice());

      orderItems.add(orderItem);
      totalPrice =
          totalPrice.add(
              product.getPrice().multiply(BigDecimal.valueOf(productRequest.getQuantity())));
    }

    order.setItems(orderItems);
    orderRepository.save(order);
    orderItemRepository.saveAll(orderItems);

    return mapToOrderResponse(order);
  }

  public OrderResponse getOrder(UUID orderId, Long customerId) throws AccessDeniedException {
    Order order =
        orderRepository
            .findByIdAndCustomerId(orderId, customerId)
            .orElseThrow(() -> new AccessDeniedException("Нет доступа к заказу"));
    return mapToOrderResponse(order);
  }

  @Transactional
  public void cancelOrder(UUID orderId, Long customerId) throws AccessDeniedException {
    Order order =
        orderRepository
            .findByIdAndCustomerId(orderId, customerId)
            .orElseThrow(() -> new AccessDeniedException("Нет доступа к заказу"));

    if (order.getStatus() != OrderStatus.CREATED) {
      throw new IllegalStateException("Удалить можно только заказ в статусе CREATED");
    }

    for (OrderItem item : order.getItems()) {
      Product product = item.getProduct();
      product.setQuantity(product.getQuantity() + item.getQuantity());
      productRepository.save(product);
    }

    order.setStatus(OrderStatus.CANCELLED);
    orderRepository.save(order);
  }

  @Transactional
  public OrderResponse updateOrder(UUID orderId, Long customerId, UpdateOrderRequest request)
      throws AccessDeniedException {
    Order order =
        orderRepository
            .findByIdAndCustomerId(orderId, customerId)
            .orElseThrow(() -> new AccessDeniedException("Нет доступа к заказу"));

    if (order.getStatus() != OrderStatus.CREATED) {
      throw new IllegalStateException("Обновить можно только заказ в статусе CREATED");
    }

    order.setDeliveryAddress(request.getDeliveryAddress());
    orderRepository.save(order);
    return mapToOrderResponse(order);
  }

  @Transactional
  public void confirmOrder(UUID orderId, Long customerId) throws AccessDeniedException {
    Order order =
        orderRepository
            .findByIdAndCustomerId(orderId, customerId)
            .orElseThrow(() -> new AccessDeniedException("Нет доступа к заказу"));

    if (order.getStatus() != OrderStatus.CREATED) {
      throw new IllegalStateException("Подтвердить можно только заказ в статусе CREATED");
    }

    order.setStatus(OrderStatus.CONFIRMED);
    orderRepository.save(order);
  }

  @Transactional
  public void completeOrder(UUID orderId, Long customerId) throws AccessDeniedException {
    Order order =
        orderRepository
            .findByIdAndCustomerId(orderId, customerId)
            .orElseThrow(() -> new AccessDeniedException("Нет доступа к заказу"));

    if (order.getStatus() != OrderStatus.CONFIRMED) {
      throw new IllegalStateException("Завершить можно только заказ в статусе CONFIRMED");
    }

    order.setStatus(OrderStatus.DONE);
    orderRepository.save(order);
  }

  private OrderResponse mapToOrderResponse(Order order) {
    OrderResponse response = new OrderResponse();
    response.setOrderId(order.getId());

    List<OrderProductResponse> productResponses =
        order.getItems().stream()
            .map(
                item -> {
                  OrderProductResponse productResponse = new OrderProductResponse();
                  productResponse.setProductId(item.getProduct().getId());
                  productResponse.setName(item.getProduct().getName());
                  productResponse.setQuantity(item.getQuantity());
                  productResponse.setPrice(item.getPriceAtOrderTime());
                  return productResponse;
                })
            .collect(Collectors.toList());

    response.setProducts(productResponses);
    response.setTotalPrice(
        productResponses.stream()
            .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add));

    return response;
  }
}

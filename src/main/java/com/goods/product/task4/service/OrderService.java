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
import com.goods.product.task5.customers.Customer;
import com.goods.product.task5.customers.CustomerRepository;
import com.goods.product.task5.orders.CustomerInfo;
import com.goods.product.task5.orders.OrderInfo;
import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final OrderItemRepository orderItemRepository;
  private final CustomerRepository сustomerRepository;

  public OrderService(
      OrderRepository orderRepository,
      ProductRepository productRepository,
      OrderItemRepository orderItemRepository,
      CustomerRepository сustomerRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
    this.orderItemRepository = orderItemRepository;
    this.сustomerRepository = сustomerRepository;
  }

  @Async
  public CompletableFuture<Map<Long, List<OrderInfo>>> getOrdersInfoForProducts(
      List<Long> productIds) {
    // 1. Получаем заказы для продуктов
    List<Order> orders = orderRepository.findOrdersByProductIds(productIds);

    // 2. Группируем заказы по productId
    Map<Long, List<OrderInfo>> result = new HashMap<>();

    for (Order order : orders) {
      for (OrderItem item : order.getItems()) {
        Long productId = item.getProduct().getId();

        // 3. Проверяем статус и добавляем в мапу
        if (order.getStatus() == OrderStatus.CREATED
            || order.getStatus() == OrderStatus.CONFIRMED) {
          OrderInfo orderInfo = new OrderInfo();
          orderInfo.setId(order.getId());
          orderInfo.setCustomer(
              new CustomerInfo(
                  order.getCustomer().getId(),
                  order.getCustomer().getAccountNumber(),
                  order.getCustomer().getEmail(),
                  order.getCustomer().getInn()));
          orderInfo.setStatus(order.getStatus());
          orderInfo.setDeliveryAddress(order.getDeliveryAddress());
          orderInfo.setQuantity(item.getQuantity());

          result.computeIfAbsent(productId, k -> new ArrayList<>()).add(orderInfo);
        }
      }
    }

    // 4. Возвращаем результат
    return CompletableFuture.completedFuture(result);
  }

  @Transactional
  public OrderResponse createOrder(Long customerId, CreateOrderRequest request) {
    Order order = new Order();

    // Ищем клиента по ID
    Customer customer =
        сustomerRepository
            .findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

    // Устанавливаем клиента в заказ
    order.setCustomer(customer);
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

  @Transactional
  public void rejectOrder(UUID orderId, Long customerId) throws AccessDeniedException {
    Order order =
        orderRepository
            .findByIdAndCustomerId(orderId, customerId)
            .orElseThrow(() -> new AccessDeniedException("Нет доступа к заказу"));

    // Проверяем, что заказ можно отклонить только из статуса CREATED
    if (order.getStatus() != OrderStatus.CREATED) {
      throw new IllegalStateException("Отклонить можно только заказ в статусе CREATED");
    }

    order.setStatus(OrderStatus.REJECTED);
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

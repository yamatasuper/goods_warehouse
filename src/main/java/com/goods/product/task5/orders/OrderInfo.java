package com.goods.product.task5.orders;

import com.goods.product.task4.model.OrderStatus;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInfo {
  private UUID id;
  private CustomerInfo customer;
  private OrderStatus status;
  private String deliveryAddress;
  private Integer quantity;
}

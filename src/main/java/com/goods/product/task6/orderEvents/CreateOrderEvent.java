package com.goods.product.task6.orderEvents;

import com.goods.product.task4.dto.request.ProductRequest;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderEvent {
  private String event;
  private Long customerId;
  private String deliveryAddress;
  private List<ProductRequest> products;
}

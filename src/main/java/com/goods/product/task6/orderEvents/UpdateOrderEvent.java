package com.goods.product.task6.orderEvents;

import com.goods.product.task4.dto.request.ProductRequest;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderEvent {
  private String event;
  private UUID orderId;
  private Long customerId;
  private List<ProductRequest> products;
}

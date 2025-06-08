package com.goods.product.task6.orderEvents;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusEvent {
  private String event;
  private UUID orderId;
  private String status;
}

package com.goods.product.task4.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderResponse {
  private UUID orderId;
  private List<OrderProductResponse> products;
  private BigDecimal totalPrice;
}

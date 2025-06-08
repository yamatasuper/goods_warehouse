package com.goods.product.task4.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderProductResponse {
  private Long productId;
  private String name;
  private int quantity;
  private BigDecimal price;
}

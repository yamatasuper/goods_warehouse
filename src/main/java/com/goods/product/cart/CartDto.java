package com.goods.product.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
  private UUID id;
  private Long customerId;
  private List<CartItemDto> items;
  private BigDecimal totalPrice;

  // Getters and setters
}

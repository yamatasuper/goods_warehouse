package com.goods.product.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {
  private Long productId;
  private int quantity;

  // Getters and setters
}

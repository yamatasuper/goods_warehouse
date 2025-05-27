package com.goods.product.cart;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
  private Long id;
  private Long productId;
  private String productName;
  private BigDecimal price;
  private String currency;
  private int quantity;
  private String imageUrl;

  // Getters and setters
}

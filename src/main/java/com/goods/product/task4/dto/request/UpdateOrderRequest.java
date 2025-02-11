package com.goods.product.task4.dto.request;

import java.util.List;
import lombok.Data;

@Data
public class UpdateOrderRequest {
  private String deliveryAddress;
  private List<ProductRequest> products;
}

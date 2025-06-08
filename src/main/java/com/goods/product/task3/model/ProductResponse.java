package com.goods.product.task3.model;

import com.goods.product.model.Product;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
  private Long id;
  private String name;
  private BigDecimal price;
  private String currency;

  // Конструктор с параметрами для инициализации объекта с Product и валютой
  public ProductResponse(Product product, String currency) {
    this.id = product.getId();
    this.name = product.getName();
    this.price = product.getPrice();
    this.currency = currency;
  }
}

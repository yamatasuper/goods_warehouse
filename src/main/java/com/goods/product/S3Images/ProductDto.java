package com.goods.product.S3Images;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
  private Long id;
  private String name;
  private String sku;
  private String description;
  private String category;
  private BigDecimal price;
  private Integer quantity;
  private String currency;
  private Boolean isAvailable;
  private LocalDateTime createdAt;
  private List<String> imageUrls; // Для хранения URL изображений
}

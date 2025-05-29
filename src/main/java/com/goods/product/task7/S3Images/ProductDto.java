package com.goods.product.task7.S3Images;

import com.goods.product.model.Product;
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

  public static ProductDto fromProduct(Product product, List<String> imageUrls) {
    ProductDto dto = new ProductDto();
    dto.setId(product.getId());
    dto.setName(product.getName());
    dto.setSku(product.getSku());
    dto.setDescription(product.getDescription());
    dto.setCategory(product.getCategory());
    dto.setPrice(product.getPrice());
    dto.setQuantity(product.getQuantity());
    dto.setCurrency(product.getCurrency());
    dto.setIsAvailable(product.getIsAvailable());
    dto.setCreatedAt(product.getCreatedAt());
    dto.setImageUrls(imageUrls);
    return dto;
  }
}

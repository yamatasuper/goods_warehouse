package com.goods.product.task7.S3Images;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductImageDto {
  private UUID id;
  private Long productId;
  private String url;
  private LocalDateTime createdAt;
}

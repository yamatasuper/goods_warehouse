package com.goods.product.task7.images.repository;

import com.goods.product.task7.images.entity.ProductImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
  List<ProductImage> findByProductId(Long productId);
}

package com.goods.product.service;

import com.goods.product.S3Images.ProductDto;
import com.goods.product.S3Images.S3Service;
import com.goods.product.model.Product;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

  private final S3Service s3Service;

  public ProductMapper(S3Service s3Service) {
    this.s3Service = s3Service;
  }

  public ProductDto toDto(Product product) {
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

    if (product.getImages() != null) {
      dto.setImageUrls(
          product.getImages().stream()
              .map(image -> s3Service.getFileUrl(image.getS3Key()))
              .collect(Collectors.toList()));
    }

    return dto;
  }
}

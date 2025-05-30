package com.goods.product.service;

import com.goods.product.model.Product;
import com.goods.product.task7.S3Images.ProductDto;
import com.goods.product.task7.S3Images.ProductImageRepository;
import com.goods.product.task7.S3Images.S3Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

  private final S3Service s3Service;
  private final ProductImageRepository productImageRepository;

  // Явный конструктор
  public ProductMapper(S3Service s3Service, ProductImageRepository productImageRepository) {
    this.s3Service = s3Service;
    this.productImageRepository = productImageRepository;
  }

  public ProductDto toDto(Product product) {
    List<String> imageUrls =
        productImageRepository.findByProductId(product.getId()).stream()
            .map(image -> s3Service.getFileUrl(image.getS3Key()))
            .collect(Collectors.toList());

    return ProductDto.fromProduct(product, imageUrls);
  }
}

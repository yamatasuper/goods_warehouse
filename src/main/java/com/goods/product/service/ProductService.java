package com.goods.product.service;

import com.goods.product.model.Product;
import com.goods.product.repository.ProductRepository;
import com.goods.product.task7.S3Images.ProductDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
    this.productRepository = productRepository;
    this.productMapper = productMapper;
  }

  // Возвращает сущность Product (для внутреннего использования)
  public Product getProductEntityById(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found"));
  }

  // Возвращает DTO (для контроллера)
  public ProductDto getProductById(Long id) {
    Product product = getProductEntityById(id);
    return productMapper.toDto(product);
  }

  public List<Product> getAllProductEntities() {
    return productRepository.findAll();
  }
}

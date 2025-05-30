package com.goods.product.controller;

import com.goods.product.model.Product;
import com.goods.product.service.ProductMapper;
import com.goods.product.service.ProductService;
import com.goods.product.task3.microservice.CurrencyConversionService;
import com.goods.product.task3.microservice.CurrencyProvider;
import com.goods.product.task7.S3Images.ProductDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;
  private final CurrencyProvider currencyProvider;
  private final CurrencyConversionService currencyConversionService;
  private final ProductMapper productMapper;

  @Autowired
  public ProductController(
      ProductService productService,
      CurrencyProvider currencyProvider,
      CurrencyConversionService currencyConversionService,
      ProductMapper productMapper) {
    this.productService = productService;
    this.currencyProvider = currencyProvider;
    this.currencyConversionService = currencyConversionService;
    this.productMapper = productMapper;
  }

  @GetMapping("/")
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    List<Product> products = productService.getAllProductEntities();
    List<ProductDto> dtos =
        products.stream().map(productMapper::toDto).collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ProductDto getProductById(@PathVariable Long id) {
    String targetCurrency = currencyProvider.getCurrency();
    Product product = productService.getProductEntityById(id);
    return convertProductToDto(product, targetCurrency);
  }

  private ProductDto convertProductToDto(Product product, String targetCurrency) {
    // Конвертируем цену
    BigDecimal convertedPrice =
        currencyConversionService.convertPrice(product.getPrice(), targetCurrency);

    ProductDto dto = new ProductDto();

    dto.setId(product.getId());
    dto.setName(product.getName());
    dto.setSku(product.getSku());
    dto.setDescription(product.getDescription());
    dto.setCategory(product.getCategory());
    dto.setPrice(convertedPrice);
    dto.setQuantity(product.getQuantity());
    dto.setCurrency(targetCurrency);
    dto.setIsAvailable(product.getIsAvailable());
    dto.setCreatedAt(product.getCreatedAt());
    return dto;
  }
}

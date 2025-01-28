package com.goods.product.controller;

import com.goods.product.model.Product;
import com.goods.product.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
  @Autowired private ProductService productService;

  @GetMapping("/")
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  @PostMapping("/")
  public Product addProduct(@RequestBody Product product) {
    return productService.saveProduct(product);
  }

  @GetMapping("/{id}")
  public Product getProductById(@PathVariable Long id) {
    return productService.getProductById(id);
  }
}

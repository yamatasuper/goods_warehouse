package com.goods.product.task2.search;

import com.goods.product.model.Product;
import com.goods.product.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductSearchController {
    private final ProductRepository productRepository;

    public ProductSearchController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestBody List<SearchCriteriaDTO> criteriaList) {

        Pageable pageable = PageRequest.of(page, size);
        DynamicSpecification<Product> spec = new DynamicSpecification<>(criteriaList);
        Page<Product> result = productRepository.findAll(spec, pageable);

        return ResponseEntity.ok(result);
    }
}




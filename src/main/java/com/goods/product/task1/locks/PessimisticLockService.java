package com.goods.product.task1.locks;

import com.goods.product.model.Product;
import com.goods.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessimisticLockService {
  @Autowired private ProductRepository productRepository;

  @Transactional
  public void updateProductNameWithLock(Long productId, String newName) {
    Product product = productRepository.findByIdAndLock(productId);
    product.setName(newName);
  }
}

package com.goods.product.task1.locks;

import com.goods.product.model.Product;
import com.goods.product.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptimisticLockService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void updateProductNameWithOptimisticLock(Long productId, String newName) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(newName); // Изменяем данные

        // Если версия будет изменена другим процессом до commit,
        // Spring выбросит OptimisticLockException
    }
}


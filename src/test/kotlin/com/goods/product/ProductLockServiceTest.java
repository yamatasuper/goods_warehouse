package com.example.project.service;

import com.example.project.entity.Product;
import com.example.project.repository.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // Запускает контекст Spring для тестов
public class ProductLockServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PessimisticLockService pessimisticLockService;

    @Autowired
    private OptimisticLockService optimisticLockService;

    @Test
    public void testPessimisticLock() throws InterruptedException {
        Long productId = 1L;

        // Создаём тестовый продукт
        Product product = new Product();
        product.setName("Test Product");
        productRepository.save(product);

        // Два потока для тестирования блокировки
        Thread thread1 = new Thread(() -> {
            pessimisticLockService.updateProductNameWithLock(productId, "Thread 1 Name");
        });

        Thread thread2 = new Thread(() -> {
            pessimisticLockService.updateProductNameWithLock(productId, "Thread 2 Name");
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        Product updatedProduct = productRepository.findById(productId).orElseThrow();
        Assertions.assertNotNull(updatedProduct.getName());
    }

    @Test
    public void testOptimisticLock() {
        Long productId = 1L;

        // Создаём тестовый продукт
        Product product = new Product();
        product.setName("Test Product");
        productRepository.save(product);

        // Загружаем продукт двумя разными процессами
        Product product1 = productRepository.findById(productId).orElseThrow();
        Product product2 = productRepository.findById(productId).orElseThrow();

        // Первый поток изменяет имя и сохраняет
        product1.setName("Thread 1 Name");
        productRepository.save(product1);

        // Второй поток пытается сохранить свои изменения
        product2.setName("Thread 2 Name");

        // Ожидаем, что произойдёт ошибка оптимистической блокировки
        Assertions.assertThrows(OptimisticLockException.class, () -> {
            productRepository.save(product2);
        });
    }
}

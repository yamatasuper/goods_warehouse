package com.goods.product.task1.scheduler;

import com.goods.product.model.Product;
import com.goods.product.repository.ProductRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceScheduler {
  @Value("${app.scheduling.enabled}")
  private boolean isSchedulingEnabled;

  @Value("${scheduling.optimization}")
  private boolean isOptimizationEnabled;

  @Autowired private ProductRepository productRepository;

  @Scheduled(fixedRate = 300000)
  public void updatePricesSimple() {
    System.out.println("Простой шедулер: Проверяем условия запуска.");
    if (isSchedulingEnabled && !isOptimizationEnabled) {
      System.out.println("Простой шедулер запущен");

      Iterable<Product> products = productRepository.findAll();
      for (Product product : products) {
        product.setPrice(product.getPrice().multiply(new BigDecimal("1.05")));
        productRepository.save(product);
      }
      System.out.println("Простой шедулер завершил работу");
    } else {
      System.out.println("Простой шедулер не запущен, так как условия не выполнены");
    }
  }

  @Scheduled(fixedRate = 60000)
  public void updatePricesOptimized() {
    System.out.println("Оптимизированный шедулер: Проверяем условия запуска.");
    if (isSchedulingEnabled && isOptimizationEnabled) {
      System.out.println("Оптимизированный шедулер запущен");

      Iterable<Product> products = productRepository.findAll();
      for (Product product : products) {
        product.setPrice(product.getPrice().multiply(new BigDecimal("1.05")));
        productRepository.save(product);
      }
      System.out.println("Оптимизированный шедулер завершил работу");
    } else {
      System.out.println("Оптимизированный шедулер не запущен, так как условия не выполнены");
    }
  }
}

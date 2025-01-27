package com.goods.product.task2.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import jakarta.annotation.PostConstruct;

@Component
public class RemarkDataInitializer {

    @Autowired
    private RemarkRepository remarkRepository;

    @PostConstruct
    public void initializeDefaultRemarks() {
        if (remarkRepository.count() == 0) { // Проверяем, что таблица пуста
            List<Remark> defaultRemarks = Arrays.asList(
                    new Remark("Invalid input data", RemarkType.ERROR),
                    new Remark("Resource not found", RemarkType.WARNING),
                    new Remark("Operation completed successfully", RemarkType.INFO),
                    new Remark("Database connection issue", RemarkType.ERROR),
                    new Remark("Unauthorized access attempt", RemarkType.WARNING),
                    new Remark("Invalid request format", RemarkType.ERROR),
                    new Remark("Service unavailable", RemarkType.ERROR)
            );

            remarkRepository.saveAll(defaultRemarks);
            System.out.println("Default remarks have been initialized.");
        }
    }
}

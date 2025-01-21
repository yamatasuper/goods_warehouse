package com.goods.product.task1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class BatchGenerationService {

    @Autowired
    private DataGeneratorService dataGeneratorService;

    public void generateData(int totalRecords, int batchSize) {
        System.out.println("Starting data generation...");
        int batchCount = (int) Math.ceil((double) totalRecords / batchSize);
        ExecutorService executor = Executors.newFixedThreadPool(4); // 4 потока

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < batchCount; i++) {
            int start = i * batchSize;
            futures.add(CompletableFuture.runAsync(() -> {
                dataGeneratorService.generateBatch(start, batchSize);
            }, executor));
        }

        // Ожидаем завершения всех потоков
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        System.out.println("Data generation completed.");
    }
}



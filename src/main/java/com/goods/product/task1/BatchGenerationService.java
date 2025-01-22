package com.goods.product.task1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class BatchGenerationService {

    @Autowired
    private DataGeneratorService dataGeneratorService;

    public void generateData(int totalRecords, int batchSize) {
        System.out.println("Starting data generation...");
        System.out.println("Total records: " + totalRecords + ", Batch size: " + batchSize);

        int batchCount = (int) Math.ceil((double) totalRecords / batchSize);
        System.out.println("Total batches to process: " + batchCount);

        // Ограничиваем количество одновременно выполняемых задач
        int maxThreads = Math.min(batchCount, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(maxThreads);

        try {
            // Обрабатываем каждый батч
            for (int i = 0; i < batchCount; i++) {
                int start = i * batchSize;
                System.out.println("Submitting batch: " + (i + 1) + "/" + batchCount + ", Start index: " + start);

                executor.submit(() -> {
                    try {
                        dataGeneratorService.generateBatch(start, batchSize);
                        System.out.println("Batch completed: Start index " + start);
                    } catch (Exception e) {
                        System.err.println("Error in batch processing: Start index " + start);
                        e.printStackTrace();
                    }
                });
            }
        } finally {
            // Завершаем executor корректно
            executor.shutdown();
            try {
                if (!executor.awaitTermination(120, TimeUnit.SECONDS)) {
                    System.err.println("Executor did not terminate in the specified time.");
                    executor.shutdownNow(); // Принудительное завершение
                }
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted during executor shutdown.");
                executor.shutdownNow();
                Thread.currentThread().interrupt(); // Восстанавливаем флаг прерывания
            }
        }

        System.out.println("Data generation completed successfully!");
    }
}



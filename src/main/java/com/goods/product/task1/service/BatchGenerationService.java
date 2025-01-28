package com.goods.product.task1.service;

import com.goods.product.task1.logs.FileLogger;
import com.goods.product.task1.logs.TimeMeasured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class BatchGenerationService {
    @Autowired
    private DataGeneratorService dataGeneratorService;

    @Autowired
    private FileLogger fileLogger;

    @TimeMeasured
    public void generateData(int totalRecords, int batchSize) {
        System.out.println("Starting data generation...");
        System.out.println("Total records: " + totalRecords + ", Batch size: " + batchSize);

        int batchCount = (int) Math.ceil((double) totalRecords / batchSize);
        System.out.println("Total batches to process: " + batchCount);

        int maxThreads = Math.min(64, Runtime.getRuntime().availableProcessors() * 4);
        ExecutorService executor = Executors.newFixedThreadPool(maxThreads);

        try {
            for (int i = 0; i < batchCount; i++) {
                int start = i * batchSize;
                executor.submit(() -> {
                    try {
                        dataGeneratorService.generateBatch(start, batchSize);

                        fileLogger.logToFile("data_generation.log",
                                "Batch completed successfully: Start index " + start +
                                        ", Batch size: " + batchSize);

                    } catch (Exception e) {
                        System.err.println("Error in batch processing: Start index " + start);
                        e.printStackTrace();

                        fileLogger.logToFile("data_generation.log",
                                "Batch failed: Start index " + start + ", Error: " + e.getMessage());
                    }
                });
            }
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(120, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Data generation completed successfully!");
    }
}

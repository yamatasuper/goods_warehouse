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

    public void generateData(int totalRecords, int batchSize) throws Exception {
        System.out.println("Starting data generation...");
        System.out.println("Total records: " + totalRecords + ", Batch size: " + batchSize);

        int batchCount = (int) Math.ceil((double) totalRecords / batchSize);
        System.out.println("Total batches to process: " + batchCount);

        ExecutorService executor = Executors.newFixedThreadPool(Math.min(batchCount, Runtime.getRuntime().availableProcessors()));

        try {
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            for (int i = 0; i < batchCount; i++) {
                int start = i * batchSize;
                System.out.println("Submitting batch: " + (i + 1) + "/" + batchCount + ", Start index: " + start);

                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        dataGeneratorService.generateBatch(start, batchSize);
                    } catch (Exception e) {
                        System.err.println("Error in batch: " + e.getMessage());
                        e.printStackTrace();
                    }
                }, executor);

                futures.add(future);
            }

            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.exceptionally(ex -> {
                System.err.println("An error occurred during batch processing: " + ex.getMessage());
                ex.printStackTrace();
                return null;
            }).join();
            System.out.println("Data generation completed successfully!");

        } finally {
            executor.shutdown();
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.err.println("Executor did not terminate in the specified time.");
                executor.shutdownNow();
            }
        }
    }
}


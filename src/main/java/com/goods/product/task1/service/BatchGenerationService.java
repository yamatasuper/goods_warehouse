package com.goods.product.task1.service;

import com.goods.product.task1.logs.FileLogger;
import com.goods.product.task1.logs.TimeMeasured;
import com.goods.product.task3.microservice.CurrencyProvider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchGenerationService {

  private final DataGeneratorService dataGeneratorService;
  private final FileLogger fileLogger;
  private final CurrencyProvider currencyProvider;

  @Autowired
  public BatchGenerationService(
      DataGeneratorService dataGeneratorService,
      FileLogger fileLogger,
      CurrencyProvider currencyProvider) {
    this.dataGeneratorService = dataGeneratorService;
    this.fileLogger = fileLogger;
    this.currencyProvider = currencyProvider;
  }

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
        String selectedCurrency = currencyProvider.getCurrency(); // Получаем валюту заранее

        executor.submit(() -> processBatch(start, batchSize, selectedCurrency));
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

  private void processBatch(int start, int batchSize, String currency) {
    try {
      dataGeneratorService.generateBatch(start, batchSize, currency);

      fileLogger.logToFile(
          "data_generation.log",
          "Batch completed successfully: Start index " + start + ", Batch size: " + batchSize);
    } catch (Exception e) {
      System.err.println("Error in batch processing: Start index " + start);
      e.printStackTrace();

      fileLogger.logToFile(
          "data_generation.log",
          "Batch failed: Start index " + start + ", Error: " + e.getMessage());
    }
  }
}

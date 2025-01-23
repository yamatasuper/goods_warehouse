package com.goods.product;

import com.goods.product.task1.service.BatchGenerationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Application implements CommandLineRunner {

    @Autowired
    private BatchGenerationService batchGenerationService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Arguments passed to the application: " + Arrays.toString(args));
        if (args.length > 0 && args[0].equals("generate-data")) {
            int totalRecords = 1000000;
            int batchSize = 10000;

            // Используем Spring-managed bean
            batchGenerationService.generateData(totalRecords, batchSize);
        }
    }
}


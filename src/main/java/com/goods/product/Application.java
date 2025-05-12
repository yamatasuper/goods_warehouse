package com.goods.product;

import com.goods.product.task1.service.BatchGenerationService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Application implements CommandLineRunner {

  @Autowired private BatchGenerationService batchGenerationService;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) {
    System.out.println("Arguments passed to the application: " + Arrays.toString(args));
    if (args.length > 0 && args[0].equals("generate-data")) {
      int totalRecords = 10000;
      int batchSize = 100;

      batchGenerationService.generateData(totalRecords, batchSize);
    }
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}

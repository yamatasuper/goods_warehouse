package com.goods.product;

import com.goods.product.task1.service.BatchGenerationService;
import com.goods.product.task7.S3Images.S3Properties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition(
    info = @Info(title = "Your API Title", version = "1.0", description = "Your API Description"))
@EnableConfigurationProperties(S3Properties.class)
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
      int totalRecords = 10;
      int batchSize = 1;

      batchGenerationService.generateData(totalRecords, batchSize);
    }
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}

package com.goods.product;

import com.goods.product.task1.DataGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private DataGeneratorService dataGeneratorService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Arguments passed to the application: " + Arrays.toString(args));
        if (args.length > 0 && args[0].equals("generate-data")) {
            dataGeneratorService.generateData(100);  // Генерация 1 миллиона записей
        }
    }

}

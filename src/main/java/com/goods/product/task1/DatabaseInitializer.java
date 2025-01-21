package com.goods.product.task1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Autowired
    private BatchGenerationService batchGenerationService;

    @Override
    public void run(String... args) throws Exception {
        String databaseName = "goods_warehouse";

        // Извлекаем базовый URL для подключения к базе
        String baseDbUrl = dbUrl.contains("/") ? dbUrl.substring(0, dbUrl.lastIndexOf('/')) : dbUrl;

        try (Connection connection = DriverManager.getConnection(baseDbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement()) {

            // Проверяем существование базы данных
            String checkDbQuery = "SELECT 1 FROM pg_database WHERE datname = '" + databaseName + "'";
            var resultSet = statement.executeQuery(checkDbQuery);

            if (!resultSet.next()) {
                // Создаём базу данных, если она не существует
                String createDbQuery = "CREATE DATABASE " + databaseName;
                statement.executeUpdate(createDbQuery);
                System.out.println("База данных '" + databaseName + "' успешно создана.");
            } else {
                System.out.println("База данных '" + databaseName + "' уже существует.");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при создании базы данных: " + e.getMessage());
            e.printStackTrace();
        }

        // После инициализации базы данных генерируем записи
        int totalRecords = 200000; // 1 млн записей
        int batchSize = 1000;       // Размер батча

        batchGenerationService.generateData(totalRecords, batchSize);
        System.out.println("Данные успешно сгенерированы.");
    }
}

package com.goods.product

import com.goods.product.features.login.configureLoginRouting
import com.goods.product.features.product.configureProductRouting
import com.goods.product.features.register.configureRegisterRouting
import com.goods.product.logger.configureStatusPages
import com.goods.product.plugins.*
import com.goods.product.swagger.configureSwagger
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database

/**
 * Основная точка входа в приложение.
 *
 * Подключается к базе данных, определяет окружение (`local` или `prod`),
 * и запускает Ktor сервер с заданной конфигурацией.
 */
fun main() {
    // Определяем окружение, локальное по умолчанию
    val environment = System.getenv("KTOR_ENV") ?: "local"

    /**
     * Подключение к базе данных на основе окружения.
     * Используются переменные окружения для подключения.
     *
     * @see System.getenv
     */
    if (environment == "local") {
        Database.connect(
            url = System.getenv("DATABASE_CONNECTION_STRING"),
            driver = "org.postgresql.Driver",
            user = System.getenv("POSTGRES_USER"),
            password = System.getenv("POSTGRES_PASSWORD")
        )
    } else {
        Database.connect(
            url = System.getenv("DATABASE_CONNECTION_STRING"),
            driver = "org.postgresql.Driver",
            user = System.getenv("POSTGRES_USER"),
            password = System.getenv("POSTGRES_PASSWORD")
        )
    }

    // Определяем порт сервера (по умолчанию 8080)
    val port = System.getenv("SERVER_PORT")?.toIntOrNull() ?: 8080

    /**
     * Запуск сервера Ktor.
     *
     * @param port Порт для запуска сервера.
     * @see Application.module
     */
    embeddedServer(
        Netty,
        port = port,
        module = Application::module // Вызывает метод module для настройки окружения
    ).start(wait = true)
}

/**
 * Основной метод конфигурации приложения.
 *
 * Загружает окружение (`local` или `prod`) и настраивает модули приложения.
 */
fun Application.module() {
    val environmentType = environment.config.propertyOrNull("ktor.deployment.environment")?.getString() ?: "local"
    log.info("Running in environment: $environmentType")

    when (environmentType) {
        "local" -> configureLocalFeatures()
        "prod" -> configureProdFeatures()
        else -> throw IllegalArgumentException("Unknown environment: $environmentType")
    }
}

/**
 * Конфигурация приложения для локального окружения.
 *
 * Настраивает маршруты, сериализацию, Swagger, и обработку ошибок.
 */
fun Application.configureLocalFeatures() {
    log.info("Configuring local features")
    configureProductRouting()
    configureSerialization()
    configureSwagger()
    configureStatusPages()
    configureRegisterRouting()
    configureLoginRouting()
}

/**
 * Конфигурация приложения для продакшн окружения.
 *
 * Настраивает маршруты, сериализацию, Swagger, и обработку ошибок.
 */
fun Application.configureProdFeatures() {
    log.info("Configuring production features")
    configureProductRouting()
    configureSerialization()
    configureSwagger()
    configureStatusPages()
    configureRegisterRouting()
    configureLoginRouting()
}

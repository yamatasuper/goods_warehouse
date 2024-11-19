package com.goods.product

import com.goods.product.features.product.configureProductRouting
import com.goods.product.logger.configureStatusPages
import com.goods.product.plugins.*
import com.goods.product.swagger.configureSwagger
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database

fun main() {
    // Подключение к базе данных
    val environment = System.getenv("KTOR_ENV") ?: "local"
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

    // Запуск сервера
    val port = System.getenv("SERVER_PORT")?.toIntOrNull() ?: 8080
    embeddedServer(
        Netty,
        port = port,
        module = Application::module // Вызывает метод module для настройки окружения
    ).start(wait = true)
}

fun Application.module() {
    val environmentType = environment.config.propertyOrNull("ktor.deployment.environment")?.getString() ?: "local"
    log.info("Running in environment: $environmentType")

    when (environmentType) {
        "local" -> configureLocalFeatures()
        "prod" -> configureProdFeatures()
        else -> throw IllegalArgumentException("Unknown environment: $environmentType")
    }
}

fun Application.configureLocalFeatures() {
    log.info("Configuring local features")
    configureProductRouting()
    configureSerialization()
    configureSwagger()
    configureStatusPages()
}

fun Application.configureProdFeatures() {
    log.info("Configuring production features")
    configureProductRouting()
    configureSerialization()
    configureSwagger()
    configureStatusPages()
}



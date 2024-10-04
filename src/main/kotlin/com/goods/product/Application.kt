package com.goods.product

import com.goods.product.features.product.configureProductRouting
import com.goods.product.plugins.*
import com.goods.product.swagger.configureSwagger
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        url = System.getenv("DATABASE_CONNECTION_STRING"),
        driver = "org.postgresql.Driver",
        user = System.getenv("POSTGRES_USER"),
        password = System.getenv("POSTGRES_PASSWORD")
    )

    embeddedServer(
        Netty,
        port = System.getenv("SERVER_PORT").toInt(),
        module = Application::goodsWarehouse
    ).start(wait = true)

//TODO: убрать локальный запуск
//    Database.connect(
//        url = "jdbc:postgresql://localhost:5432/artemmovchan",
//        driver = "org.postgresql.Driver",
//        user = "postgres",
//    )
//
//    embeddedServer(
//        Netty,
//        port = 8080,
//        module = Application::goodsWarehouse
//    ).start(wait = true)
}
fun Application.goodsWarehouse() {
    configureProductRouting()
    configureSerialization()
    configureSwagger()
}


package com.goods.product.swagger

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

//fun Application.configureSwagger() {
//    routing {
//        openAPI(path = "openapi")
//        swaggerUI(path = "swagger")
//    }
//}

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.Components

fun Application.configureSwagger() {
    val openApi = OpenAPI().apply {
        info = Info().apply {
            title = "Product API"
            version = "1.0.0"
            description = "API для загрузки данных о продуктах и складах"
        }
        components = Components() // Инициализация компонентов
    }

    // Загрузите ваши пути из YAML
//    openApi.paths = parseYaml("path/to/your/documentation.yaml")

    routing {
        openAPI(path = "openapi")
        swaggerUI(path = "swagger")
    }
}



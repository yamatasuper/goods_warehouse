package com.goods.product.swagger

import io.ktor.server.application.Application
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.routing
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
        components = Components()
    }

    routing {
        openAPI(path = "openapi")
        swaggerUI(path = "swagger")
    }
}



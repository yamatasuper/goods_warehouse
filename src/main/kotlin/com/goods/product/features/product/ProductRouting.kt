package com.goods.product.features.product

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureProductRouting() {

    routing {
        get("/loadWarehouses") {
            val coursesController = ProductController(call)
            coursesController.getProductData()
        }
    }
}
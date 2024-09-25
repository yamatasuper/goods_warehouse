package com.goods.goods.features.goods

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureWarehouseRouting() {

    routing {
        get("/loadWarehouses") {
            val coursesController = CoursesController(call)
            coursesController.performCoursesData()
        }
    }
}
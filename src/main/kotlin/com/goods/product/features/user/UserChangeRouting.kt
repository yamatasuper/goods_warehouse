package com.goods.product.features.user


import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.userChangeRouting() {
    routing {
        post("/userChange") {
            val userChangeController = UserChangeController(call)
            userChangeController.userChangeData()
        }
    }
}
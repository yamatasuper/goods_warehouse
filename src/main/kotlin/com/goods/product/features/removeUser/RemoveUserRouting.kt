package com.goods.product.features.removeUser

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureLogoutRouting() {

    routing {
        post("/removeUser") {
            val removeUserController = RemoveUserController(call)
            removeUserController.performRemoveUser()
        }
    }
}
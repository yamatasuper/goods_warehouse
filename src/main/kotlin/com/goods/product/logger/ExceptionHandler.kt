package com.goods.product.logger

import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.plugins.statuspages.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<InvalidMethodException> { call, cause ->
            call.respond(
                status = HttpStatusCode.MethodNotAllowed,
                message = mapOf(
                    "error" to cause.message,
                    "details" to cause.details
                )
            )
        }

        status(HttpStatusCode.NotFound) { call, status ->
            call.respondText(
                text = "404: The requested resource was not found",
                status = status
            )
        }

        exception<Throwable> { call, cause ->
            call.respondText(
                text = "500: ${cause.message ?: "Unknown error occurred"}",
                status = HttpStatusCode.InternalServerError
            )
        }
    }
}

class InvalidMethodException(
    override val message: String,
    val details: Map<String, String> = emptyMap()
) : RuntimeException(message)
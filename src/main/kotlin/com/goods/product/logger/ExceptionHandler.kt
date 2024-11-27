package com.goods.product.logger

import com.goods.product.utils.Strings
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
                text = Strings.ERROR_NOT_FOUND,
                status = status
            )
        }

        exception<Throwable> { call, cause ->
            call.respondText(
                text = "500: ${cause.message ?: Strings.ERROR_INTERNAL_SERVER}}",
                status = HttpStatusCode.InternalServerError
            )
        }
    }
}

class InvalidMethodException(
    override val message: String,
    val details: Map<String, String> = emptyMap()
) : RuntimeException(message)
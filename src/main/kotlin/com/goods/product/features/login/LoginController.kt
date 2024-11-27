package com.goods.product.features.login

import com.goods.product.database.users.Users
import com.goods.product.utils.Strings
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond

class LoginController(private val call: ApplicationCall) {
    suspend fun performLogin() {
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUserUsingEmail(receive.email)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, Strings.ERROR_USER_NOT_FOUND)
        } else {
            if (userDTO.password == receive.password) {
                call.respond(userDTO.token)
            } else {
                call.respond(HttpStatusCode.BadRequest, Strings.ERROR_INVALID_PASSWORD)
            }
        }
    }
}
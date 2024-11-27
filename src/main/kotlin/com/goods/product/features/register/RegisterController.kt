package com.goods.product.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import com.goods.product.database.tokens.TokenDTO
import com.goods.product.database.tokens.Tokens
import com.goods.product.database.users.UserDTO
import com.goods.product.database.users.Users
import com.goods.product.utils.Strings
import com.goods.product.utils.isValidEmail
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        if (!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest,  Strings.ERROR_INVALID_EMAIL)
        }

        val userDTO = Users.fetchUserUsingToken(registerReceiveRemote.email)
        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, Strings.ERROR_USER_ALREADY_EXISTS)
        } else {
            val token = UUID.randomUUID().toString()

            try {
                Users.insert(
                    UserDTO(
                        password = registerReceiveRemote.password,
                        email = registerReceiveRemote.email,
                        username = registerReceiveRemote.username,
                        token = token
                    )
                )
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, Strings.ERROR_USER_ALREADY_EXISTS)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "${Strings.ERROR_USER_CREATION_FAILED} ${e.localizedMessage}")
            }

            Tokens.insert(
                TokenDTO(
                    id = UUID.randomUUID().toString(), email = registerReceiveRemote.email,
                    token = token
                )
            )

            call.respond(RegisterResponseRemote(token = token))
        }
    }
}
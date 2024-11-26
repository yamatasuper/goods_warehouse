package com.goods.product.features.removeUser

import com.goods.product.database.tokens.Tokens
import com.goods.product.database.users.Users
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond

class RemoveUserController(private val call: ApplicationCall) {

    suspend fun performRemoveUser() {
        val receive = call.receive<RemoveUserReceiveRemote>()
        val removeUserDTO = Users.removeUserUsingToken(receive.token)
        val removeTokenUserDTO = Tokens.removeUserUsingToken(receive.token)

        if (removeUserDTO == 0 && removeTokenUserDTO == 0) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else if (removeUserDTO == 1 && removeTokenUserDTO == 1) {
            call.respond(HttpStatusCode.OK, UserLogoutResponseRemote("ok"))
        }
    }
}
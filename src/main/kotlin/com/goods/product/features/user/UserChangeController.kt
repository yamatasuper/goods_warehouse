package com.goods.product.features.user

import com.goods.product.database.users.Users
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond


class UserChangeController(private val call: ApplicationCall) {
    suspend fun userChangeData() {
        val userChangeReceiveRemote = call.receive<UserChangeReceiveRemote>()
        val userChangeResponse = Users.userChangeData(userChangeReceiveRemote)
        call.respond(userChangeResponse)
    }
}
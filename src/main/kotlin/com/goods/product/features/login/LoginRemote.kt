package com.goods.product.features.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginReceiveRemote(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponseRemote(
    val token: String
)

@Serializable
data class UserDataResponseRemote(
    val email: String,
    val userName: String
)

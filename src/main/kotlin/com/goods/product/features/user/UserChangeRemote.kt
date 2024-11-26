package com.goods.product.features.user

import kotlinx.serialization.Serializable

@Serializable
data class UserChangeReceiveRemote(
    val email: String,
    val password: String,
    val username: String
)

@Serializable
data class UserChangeResponseRemote(
    val code: String
)

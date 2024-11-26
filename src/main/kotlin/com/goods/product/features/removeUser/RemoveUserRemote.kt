package com.goods.product.features.removeUser

import kotlinx.serialization.Serializable

@Serializable
data class RemoveUserReceiveRemote(
    val token: String,
)
@Serializable
data class UserLogoutResponseRemote(
    val status: String
)
package com.goods.product.features.product

import kotlinx.serialization.Serializable

@Serializable
data class ProductRemoteModel(
    val id: Int,
    val name: String,
    val sku: String,
    val description: String?,
    val category: String?,
    val price: Double,
    val quantity: Int,
    val lastQuantityUpdate: String,
    val createdAt: String
)

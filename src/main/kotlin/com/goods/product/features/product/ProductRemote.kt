package com.goods.product.features.product

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ProductRemoteModel(
    @Contextual val id: UUID,
    val name: String,
    val sku: String,
    val description: String?,
    val category: String?,
    val price: Double,
    val quantity: Int,
    val lastQuantityUpdate: String,
    val createdAt: String
)

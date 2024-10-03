package com.goods.product.database.product

import com.goods.product.features.product.ProductRemoteModel
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object ProductTable : Table() {
    val id = integer("id")
    val name = varchar("name", 255)
    val sku = varchar("sku", 100).uniqueIndex()
    val description = text("description").nullable()
    val category = varchar("category", 100).nullable()
    val price = decimal("price", 10, 2)
    val quantity = integer("quantity")
    val lastQuantityUpdate = varchar("last_quantity_update", 100).nullable()
    val createdAt = varchar("created_at", 100).nullable()

    fun fetchProduct(): List<ProductRemoteModel> {
        return try {
            transaction {
                ProductTable.selectAll().map {
                    ProductRemoteModel(
                        id = it[ProductTable.id],
                        name = it[ProductTable.name],
                        sku = it[ProductTable.sku],
                        description = it[ProductTable.description],
                        category = it[ProductTable.category],
                        price = it[ProductTable.price].toDouble(),
                        quantity = it[ProductTable.quantity],
                        lastQuantityUpdate = it[ProductTable.lastQuantityUpdate].toString(),
                        createdAt = it[ProductTable.createdAt].toString()
                    )
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
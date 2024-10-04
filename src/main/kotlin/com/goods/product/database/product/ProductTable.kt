package com.goods.product.database.product

import com.goods.product.features.product.ProductRemoteModel
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


data class ProductValidationResult(val isValid: Boolean, val errors: List<String> = emptyList())

object ProductTable : Table() {
    val id = uuid("id")
    val name = varchar("name", 255)
    val sku = varchar("sku", 100).uniqueIndex()
    val description = text("description").nullable()
    val category = varchar("category", 100).nullable()
    val price = decimal("price", 10, 2)
    val quantity = integer("quantity")
    val lastQuantityUpdate = varchar("last_quantity_update", 100).nullable()
    val createdAt = varchar("created_at", 100).nullable()

    fun validateProduct(product: ProductRemoteModel): ProductValidationResult {
        val errors = mutableListOf<String>()

        if (product.name.isBlank()) errors.add("Название товара не должно быть пустым.")
        if (product.sku.isBlank()) errors.add("SKU не должно быть пустым.")
        if (product.price < 0) errors.add("Цена не должна быть отрицательной.")
        if (product.quantity < 0) errors.add("Количество не должно быть отрицательным.")

        return ProductValidationResult(errors.isEmpty(), errors)
    }

    fun fetchProduct(): List<ProductRemoteModel> {
        return try {
            transaction {
                ProductTable.selectAll().mapNotNull {
                    val product = ProductRemoteModel(
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

                    val validationResult = validateProduct(product)
                    if (validationResult.isValid) product else null
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
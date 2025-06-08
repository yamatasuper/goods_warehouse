//package com.goods.product.database.product
//
//import com.goods.product.features.product.ProductRemoteModel
//import com.goods.product.utils.ProductDatabaseColumns
//import com.goods.product.utils.Strings
//import org.jetbrains.exposed.sql.Table
//import org.jetbrains.exposed.sql.selectAll
//import org.jetbrains.exposed.sql.transactions.transaction
//
//data class ProductValidationResult(val isValid: Boolean, val errors: List<String> = emptyList())
//
//object ProductTable : Table() {
//    val id = uuid(ProductDatabaseColumns.ID)
//    val name = varchar(ProductDatabaseColumns.NAME, 255)
//    val sku = varchar(ProductDatabaseColumns.SKU, 100).uniqueIndex()
//    val description = text(ProductDatabaseColumns.DESCRIPTION).nullable()
//    val category = varchar(ProductDatabaseColumns.CATEGORY, 100).nullable()
//    val price = decimal(ProductDatabaseColumns.PRICE, 10, 2)
//    val quantity = integer(ProductDatabaseColumns.QUANTITY)
//    val lastQuantityUpdate = varchar(ProductDatabaseColumns.LAST_QUANTITY_UPDATE, 100).nullable()
//    val createdAt = varchar(ProductDatabaseColumns.CREATED_AT, 100).nullable()
//
//    fun validateProduct(product: ProductRemoteModel): ProductValidationResult {
//        val errors = mutableListOf<String>()
//
//        if (product.name.isBlank()) errors.add(Strings.ERROR_PRODUCT_NAME_EMPTY)
//        if (product.sku.isBlank()) errors.add(Strings.ERROR_PRODUCT_SKU_EMPTY)
//        if (product.price < 0) errors.add(Strings.ERROR_PRODUCT_PRICE_NEGATIVE)
//        if (product.quantity < 0) errors.add(Strings.ERROR_PRODUCT_QUANTITY_NEGATIVE)
//
//        return ProductValidationResult(errors.isEmpty(), errors)
//    }
//
//    fun fetchProduct(): List<ProductRemoteModel> {
//        return try {
//            transaction {
//                ProductTable.selectAll().mapNotNull {
//                    val product = ProductRemoteModel(
//                        id = it[ProductTable.id],
//                        name = it[ProductTable.name],
//                        sku = it[ProductTable.sku],
//                        description = it[ProductTable.description],
//                        category = it[ProductTable.category],
//                        price = it[ProductTable.price].toDouble(),
//                        quantity = it[ProductTable.quantity],
//                        lastQuantityUpdate = it[ProductTable.lastQuantityUpdate].toString(),
//                        createdAt = it[ProductTable.createdAt].toString()
//                    )
//
//                    val validationResult = validateProduct(product)
//                    if (validationResult.isValid) product else null
//                }
//            }
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }
//}
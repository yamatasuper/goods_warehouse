package com.goods.goods.database.goods

import com.goods.goods.features.goods.WarehouseRemoteModel
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object GoodsTable : Table() {
    val id = integer("id")
    val name = varchar("name", 255)
    val sku = varchar("sku", 100).uniqueIndex()
    val description = text("description").nullable()
    val category = varchar("category", 100).nullable()
    val price = decimal("price", 10, 2)
    val quantity = integer("quantity")
    val lastQuantityUpdate = varchar("last_quantity_update", 100).nullable()
    val createdAt = varchar("created_at", 100).nullable()

    fun fetchGoods(): List<WarehouseRemoteModel> {
        return try {
            transaction {
                GoodsTable.selectAll().map {
                    WarehouseRemoteModel(
                        id = it[GoodsTable.id],
                        name = it[GoodsTable.name],
                        sku = it[GoodsTable.sku],
                        description = it[GoodsTable.description],
                        category = it[GoodsTable.category],
                        price = it[GoodsTable.price].toDouble(),
                        quantity = it[GoodsTable.quantity],
                        lastQuantityUpdate = it[GoodsTable.lastQuantityUpdate].toString(),
                        createdAt = it[GoodsTable.createdAt].toString()
                    )
                }
            }
        } catch (e: Exception) {
            emptyList() // Возвращаем пустой список в случае ошибки
        }
    }
}
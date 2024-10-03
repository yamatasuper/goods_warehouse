package com.goods.product.features.product

import com.goods.product.database.product.ProductTable
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class ProductController(private val call: ApplicationCall) {
    suspend fun getProductData() {
        val productData = ProductTable.fetchProduct()
        call.respond(HttpStatusCode.OK, productData)
    }
}
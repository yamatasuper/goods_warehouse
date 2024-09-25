package com.goods.goods.features.goods

import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class WarehouseController(private val call: ApplicationCall) {

    suspend fun getWarehouseGoodsData() {
        val coursesCategories = CoursesCategories.fetchCoursesCategories()
        call.respond(coursesCategories)
    }
}
package com.goods.product.utils

object Strings {
    const val ERROR_NOT_FOUND = "404: The requested resource was not found"
    const val ERROR_INTERNAL_SERVER = "500: Unknown error occurred"

    const val ERROR_PRODUCT_NAME_EMPTY = "Название товара не должно быть пустым."
    const val ERROR_PRODUCT_SKU_EMPTY = "SKU не должно быть пустым."
    const val ERROR_PRODUCT_PRICE_NEGATIVE = "Цена не должна быть отрицательной."
    const val ERROR_PRODUCT_QUANTITY_NEGATIVE = "Количество не должно быть отрицательным."

    const val ERROR_USER_NOT_FOUND = "Пользователь не найден."
    const val ERROR_INVALID_PASSWORD = "Неверный пароль."

    const val ERROR_INVALID_EMAIL = "Email is not valid"
    const val ERROR_USER_ALREADY_EXISTS = "User already exists"
    const val ERROR_USER_CREATION_FAILED = "Can't create user"
}

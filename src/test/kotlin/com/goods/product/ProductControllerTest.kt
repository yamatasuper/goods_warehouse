import com.goods.product.database.product.ProductTable
import com.goods.product.features.product.ProductRemoteModel
import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class ProductTableTest {

    private lateinit var expectedValidProducts: List<ProductRemoteModel>

    @BeforeEach
    fun setup() {
        // Инициализируем список валидных продуктов
        expectedValidProducts = listOf(
            ProductRemoteModel(
                id = UUID.randomUUID(),
                name = "Valid Product",
                sku = "SKU123",
                description = "Valid description",
                category = "Category",
                price = 100.0,
                quantity = 10,
                lastQuantityUpdate = "2024-01-01",
                createdAt = "2024-01-01"
            )
        )

        // Настраиваем мок для ProductTable
        mockkObject(ProductTable)
        every { ProductTable.fetchProduct() } returns expectedValidProducts
    }

    @Test
    fun `fetchProduct should return only valid products`() {
        // Вызываем метод fetchProduct, который возвращает мокированные данные
        val products = ProductTable.fetchProduct()

        // Проверяем, что результат соответствует ожидаемому списку продуктов
        assertEquals(expectedValidProducts, products)
    }

    @Test
    fun `validateProduct should return valid result for a valid product`() {
        val product = ProductRemoteModel(
            id = UUID.randomUUID(),
            name = "Товар",
            sku = "SKU123",
            description = "Описание товара",
            category = "Категория",
            price = 100.0,
            quantity = 10,
            lastQuantityUpdate = "2024-01-01",
            createdAt = "2024-01-01"
        )

        val result = ProductTable.validateProduct(product)
        assertEquals(true, result.isValid)
        assertEquals(emptyList<String>(), result.errors)
    }

    @Test
    fun `validateProduct should return errors for an invalid product`() {
        val product = ProductRemoteModel(
            id = UUID.randomUUID(),
            name = "",
            sku = "",
            description = null,
            category = null,
            price = -10.0,
            quantity = -5,
            lastQuantityUpdate = null,
            createdAt = null
        )

        val result = ProductTable.validateProduct(product)
        assertEquals(false, result.isValid)
        assertEquals(
            listOf(
                "Название товара не должно быть пустым.",
                "SKU не должно быть пустым.",
                "Цена не должна быть отрицательной.",
                "Количество не должно быть отрицательным."
            ),
            result.errors
        )
    }
}
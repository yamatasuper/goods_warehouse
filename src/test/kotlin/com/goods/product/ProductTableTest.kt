import com.goods.product.database.product.ProductTable
import com.goods.product.features.product.ProductRemoteModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SchemaUtils.drop
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductTableTest {

    @Before
    fun setup() {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
        transaction {
            create(ProductTable)
        }
    }

    @After
    fun teardown() {
        transaction {
            drop(ProductTable)
        }
    }

    @Test
    fun `validateProduct should return valid result for valid product`() {
        val validProduct = ProductRemoteModel(
            id = UUID.randomUUID(),
            name = "Test Product",
            sku = "SKU12345",
            description = "Test description",
            category = "Category1",
            price = 100.0,
            quantity = 10,
            lastQuantityUpdate = "2023-11-01",
            createdAt = "2023-11-01"
        )
        val validationResult = ProductTable.validateProduct(validProduct)
        assertTrue(validationResult.isValid)
        assertTrue(validationResult.errors.isEmpty())
    }

    @Test
    fun `validateProduct should return errors for invalid product`() {
        val invalidProduct = ProductRemoteModel(
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
        val validationResult = ProductTable.validateProduct(invalidProduct)
        assertTrue(!validationResult.isValid)
        assertEquals(4, validationResult.errors.size)
    }

    @Test
    fun `fetchProduct should return valid products from database`() {
        transaction {
            ProductTable.insert {
                it[id] = UUID.randomUUID()
                it[name] = "Test Product"
                it[sku] = "SKU12345"
                it[description] = "Test description"
                it[category] = "Category1"
                it[price] = 100.toBigDecimal()
                it[quantity] = 10
                it[lastQuantityUpdate] = "2023-11-01"
                it[createdAt] = "2023-11-01"
            }
        }

        val products = ProductTable.fetchProduct()
        assertEquals(1, products.size)
        assertEquals("Test Product", products[0].name)
    }

    @Test
    fun `fetchProduct should skip invalid products`() {
        transaction {
            ProductTable.insert {
                it[id] = UUID.randomUUID()
                it[name] = ""
                it[sku] = ""
                it[description] = null
                it[category] = null
                it[price] = (-10).toBigDecimal()
                it[quantity] = -5
                it[lastQuantityUpdate] = null
                it[createdAt] = null
            }
        }

        val products = ProductTable.fetchProduct()
        assertTrue(products.isEmpty())
    }
}

//package com.goods.product.features.product
//
//import kotlinx.serialization.Serializable
//import java.util.UUID
//
//import kotlinx.serialization.KSerializer
//import kotlinx.serialization.descriptors.PrimitiveKind
//import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
//import kotlinx.serialization.encoding.Decoder
//import kotlinx.serialization.encoding.Encoder
//
//object UUIDSerializer : KSerializer<UUID> {
//    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)
//
//    override fun serialize(encoder: Encoder, value: UUID) {
//        encoder.encodeString(value.toString())
//    }
//
//    override fun deserialize(decoder: Decoder): UUID {
//        return UUID.fromString(decoder.decodeString())
//    }
//}
//
//@Serializable
//data class ProductRemoteModel(
//    @Serializable(with = UUIDSerializer::class) val id: UUID,
//    val name: String,
//    val sku: String,
//    val description: String?,
//    val category: String?,
//    val price: Double,
//    val quantity: Int,
//    val lastQuantityUpdate: String?,
//    val createdAt: String?
//)

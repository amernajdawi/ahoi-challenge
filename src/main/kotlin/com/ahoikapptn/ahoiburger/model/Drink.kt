package com.ahoikapptn.ahoiburger.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.math.BigDecimal
import java.time.Instant

@Document(collection = "drinks")
data class Drink(
    @Id val id: String? = null,
    val name: String,
    val image: String? = null,
    @Field(targetType = FieldType.DECIMAL128) val price: BigDecimal,
    val description: String? = null,
    val specialIngredients: List<String> = emptyList(),
    val allergens: List<String> = emptyList(),
    val available: Boolean = true,
    @CreatedDate val createdAt: Instant? = null,
    @LastModifiedDate val updatedAt: Instant? = null
)

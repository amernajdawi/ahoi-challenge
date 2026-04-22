package com.ahoikapptn.ahoiburger.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class DrinkRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,

    val image: String? = null,

    @field:Positive(message = "Price must be positive")
    val price: BigDecimal,

    val description: String? = null,
    val specialIngredients: List<String> = emptyList(),
    val allergens: List<String> = emptyList(),
    val available: Boolean = true
)

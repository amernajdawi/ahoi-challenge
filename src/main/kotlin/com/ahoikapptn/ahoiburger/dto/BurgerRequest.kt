package com.ahoikapptn.ahoiburger.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class BurgerRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,

    val image: String? = null,

    @field:Positive(message = "Price must be positive")
    val price: BigDecimal,

    @field:Positive(message = "Weight must be positive")
    val weightInGrams: Int,

    val vegetarian: Boolean = false,
    val description: String? = null,
    val specialIngredients: List<String> = emptyList(),
    val allergens: List<String> = emptyList(),
    val available: Boolean = true
)

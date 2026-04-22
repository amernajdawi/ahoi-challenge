package com.ahoikapptn.ahoiburger.controller

import com.ahoikapptn.ahoiburger.dto.BurgerRequest
import com.ahoikapptn.ahoiburger.model.Burger
import com.ahoikapptn.ahoiburger.service.BurgerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/burgers")
@Tag(name = "Burgers", description = "Manage burger menu items")
class BurgerController(private val service: BurgerService) {

    @GetMapping
    @Operation(summary = "List burgers", description = "Returns all burgers. Supports filtering by name, price range, vegetarian, and availability.")
    fun getAll(
        @Parameter(description = "Filter by name (case-insensitive partial match)")
        @RequestParam name: String? = null,
        @Parameter(description = "Minimum price (USD)")
        @RequestParam minPrice: BigDecimal? = null,
        @Parameter(description = "Maximum price (USD)")
        @RequestParam maxPrice: BigDecimal? = null,
        @Parameter(description = "Filter vegetarian burgers only")
        @RequestParam vegetarian: Boolean? = null,
        @Parameter(description = "Filter by availability")
        @RequestParam available: Boolean? = null
    ): List<Burger> = service.findAll(name, minPrice, maxPrice, vegetarian, available)

    @GetMapping("/{id}")
    @Operation(summary = "Get burger by ID")
    fun getById(@PathVariable id: String): Burger = service.findById(id)

    @PostMapping
    @Operation(summary = "Create a burger")
    fun create(@Valid @RequestBody request: BurgerRequest): ResponseEntity<Burger> =
        ResponseEntity.status(HttpStatus.CREATED).body(service.create(request.toBurger()))

    @PutMapping("/{id}")
    @Operation(summary = "Update a burger")
    fun update(@PathVariable id: String, @Valid @RequestBody request: BurgerRequest): Burger =
        service.update(id, request.toBurger())

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a burger")
    fun delete(@PathVariable id: String) = service.delete(id)

    private fun BurgerRequest.toBurger() = Burger(
        name = name,
        image = image,
        price = price,
        weightInGrams = weightInGrams,
        vegetarian = vegetarian,
        description = description,
        specialIngredients = specialIngredients,
        allergens = allergens,
        available = available
    )
}

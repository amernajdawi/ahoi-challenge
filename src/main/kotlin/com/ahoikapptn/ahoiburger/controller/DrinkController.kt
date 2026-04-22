package com.ahoikapptn.ahoiburger.controller

import com.ahoikapptn.ahoiburger.dto.DrinkRequest
import com.ahoikapptn.ahoiburger.model.Drink
import com.ahoikapptn.ahoiburger.service.DrinkService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/drinks")
@Tag(name = "Drinks", description = "Manage drink menu items")
class DrinkController(private val service: DrinkService) {

    @GetMapping
    @Operation(summary = "List drinks", description = "Returns all drinks. Supports filtering by name, price range, and availability.")
    fun getAll(
        @Parameter(description = "Filter by name (case-insensitive partial match)")
        @RequestParam name: String? = null,
        @Parameter(description = "Minimum price (USD)")
        @RequestParam minPrice: BigDecimal? = null,
        @Parameter(description = "Maximum price (USD)")
        @RequestParam maxPrice: BigDecimal? = null,
        @Parameter(description = "Filter by availability")
        @RequestParam available: Boolean? = null
    ): List<Drink> = service.findAll(name, minPrice, maxPrice, available)

    @GetMapping("/{id}")
    @Operation(summary = "Get drink by ID")
    fun getById(@PathVariable id: String): Drink = service.findById(id)

    @PostMapping
    @Operation(summary = "Create a drink")
    fun create(@Valid @RequestBody request: DrinkRequest): ResponseEntity<Drink> =
        ResponseEntity.status(HttpStatus.CREATED).body(service.create(request.toDrink()))

    @PutMapping("/{id}")
    @Operation(summary = "Update a drink")
    fun update(@PathVariable id: String, @Valid @RequestBody request: DrinkRequest): Drink =
        service.update(id, request.toDrink())

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a drink")
    fun delete(@PathVariable id: String) = service.delete(id)

    private fun DrinkRequest.toDrink() = Drink(
        name = name,
        image = image,
        price = price,
        description = description,
        specialIngredients = specialIngredients,
        allergens = allergens,
        available = available
    )
}

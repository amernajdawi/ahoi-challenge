package com.ahoikapptn.ahoiburger.service

import com.ahoikapptn.ahoiburger.exception.NotFoundException
import com.ahoikapptn.ahoiburger.model.Drink
import com.ahoikapptn.ahoiburger.repository.DrinkRepository
import org.bson.types.Decimal128
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class DrinkService(
    private val repository: DrinkRepository,
    private val mongo: MongoTemplate
) {

    fun findAll(
        name: String?,
        minPrice: BigDecimal?,
        maxPrice: BigDecimal?,
        available: Boolean?
    ): List<Drink> {
        val criteria = mutableListOf<Criteria>()

        name?.let { criteria.add(Criteria.where("name").regex(it, "i")) }
        available?.let { criteria.add(Criteria.where("available").`is`(it)) }

        if (minPrice != null || maxPrice != null) {
            val priceCriteria = Criteria.where("price")
            minPrice?.let { priceCriteria.gte(Decimal128(it)) }
            maxPrice?.let { priceCriteria.lte(Decimal128(it)) }
            criteria.add(priceCriteria)
        }

        val query = if (criteria.isEmpty()) Query()
                    else Query(Criteria().andOperator(*criteria.toTypedArray()))

        return mongo.find(query, Drink::class.java)
    }

    fun findById(id: String): Drink =
        repository.findById(id).orElseThrow { NotFoundException("Drink", id) }

    fun create(drink: Drink): Drink = repository.save(drink)

    fun update(id: String, drink: Drink): Drink {
        repository.findById(id).orElseThrow { NotFoundException("Drink", id) }
        return repository.save(drink.copy(id = id))
    }

    fun delete(id: String) {
        repository.findById(id).orElseThrow { NotFoundException("Drink", id) }
        repository.deleteById(id)
    }
}

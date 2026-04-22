package com.ahoikapptn.ahoiburger.service

import com.ahoikapptn.ahoiburger.exception.NotFoundException
import com.ahoikapptn.ahoiburger.model.Burger
import com.ahoikapptn.ahoiburger.repository.BurgerRepository
import org.bson.types.Decimal128
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BurgerService(
    private val repository: BurgerRepository,
    private val mongo: MongoTemplate
) {

    fun findAll(
        name: String?,
        minPrice: BigDecimal?,
        maxPrice: BigDecimal?,
        vegetarian: Boolean?,
        available: Boolean?
    ): List<Burger> {
        val criteria = mutableListOf<Criteria>()

        name?.let { criteria.add(Criteria.where("name").regex(it, "i")) }
        vegetarian?.let { criteria.add(Criteria.where("vegetarian").`is`(it)) }
        available?.let { criteria.add(Criteria.where("available").`is`(it)) }

        if (minPrice != null || maxPrice != null) {
            val priceCriteria = Criteria.where("price")
            minPrice?.let { priceCriteria.gte(Decimal128(it)) }
            maxPrice?.let { priceCriteria.lte(Decimal128(it)) }
            criteria.add(priceCriteria)
        }

        val query = if (criteria.isEmpty()) Query()
                    else Query(Criteria().andOperator(*criteria.toTypedArray()))

        return mongo.find(query, Burger::class.java)
    }

    fun findById(id: String): Burger =
        repository.findById(id).orElseThrow { NotFoundException("Burger", id) }

    fun create(burger: Burger): Burger = repository.save(burger)

    fun update(id: String, burger: Burger): Burger {
        repository.findById(id).orElseThrow { NotFoundException("Burger", id) }
        return repository.save(burger.copy(id = id))
    }

    fun delete(id: String) {
        repository.findById(id).orElseThrow { NotFoundException("Burger", id) }
        repository.deleteById(id)
    }
}

package com.ahoikapptn.ahoiburger.repository

import com.ahoikapptn.ahoiburger.model.Drink
import org.springframework.data.mongodb.repository.MongoRepository

interface DrinkRepository : MongoRepository<Drink, String>

package com.ahoikapptn.ahoiburger.repository

import com.ahoikapptn.ahoiburger.model.Burger
import org.springframework.data.mongodb.repository.MongoRepository

interface BurgerRepository : MongoRepository<Burger, String>

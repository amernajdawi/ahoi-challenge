package com.ahoikapptn.ahoiburger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing

@SpringBootApplication
@EnableMongoAuditing
@OpenAPIDefinition(
    info = Info(
        title = "Ahoi Burger API",
        version = "1.0",
        description = "REST API for managing burgers and drinks"
    )
)
class AhoiBurgerApplication

fun main(args: Array<String>) {
	runApplication<AhoiBurgerApplication>(*args)
}

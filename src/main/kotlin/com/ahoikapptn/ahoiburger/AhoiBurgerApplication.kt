package com.ahoikapptn.ahoiburger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
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
    ),
    security = [SecurityRequirement(name = "X-API-Key")]
)
@SecurityScheme(
    name = "X-API-Key",
    type = SecuritySchemeType.APIKEY,
    `in` = SecuritySchemeIn.HEADER,
    paramName = "X-API-Key"
)
class AhoiBurgerApplication

fun main(args: Array<String>) {
	runApplication<AhoiBurgerApplication>(*args)
}

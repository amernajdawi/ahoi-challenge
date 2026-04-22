package com.ahoikapptn.ahoiburger.controller

import com.ahoikapptn.ahoiburger.exception.NotFoundException
import com.ahoikapptn.ahoiburger.model.Burger
import com.ahoikapptn.ahoiburger.service.BurgerService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.math.BigDecimal

@WebMvcTest(BurgerController::class)
@TestPropertySource(properties = [
    "springdoc.api-docs.enabled=false",
    "springdoc.swagger-ui.enabled=false",
    "api.key=test-key"
])
class BurgerControllerTest {

    @Autowired private lateinit var mockMvc: MockMvc
    @MockitoBean private lateinit var service: BurgerService
    @MockitoBean(name = "mongoMappingContext") private lateinit var mongoMappingContext: MongoMappingContext

    private val apiKey = "test-key"

    private val burger = Burger(
        id = "1",
        name = "Ahoi Classic",
        price = BigDecimal("9.99"),
        weightInGrams = 250
    )

    @Test
    fun `GET all burgers returns 200`() {
        `when`(service.findAll(null, null, null, null, null)).thenReturn(listOf(burger))

        mockMvc.get("/api/burgers") { header("X-API-Key", apiKey) }
            .andExpect {
                status { isOk() }
                jsonPath("$[0].name") { value("Ahoi Classic") }
            }
    }

    @Test
    fun `GET burger by id returns 200 when found`() {
        `when`(service.findById("1")).thenReturn(burger)

        mockMvc.get("/api/burgers/1") { header("X-API-Key", apiKey) }
            .andExpect {
                status { isOk() }
                jsonPath("$.id") { value("1") }
            }
    }

    @Test
    fun `GET burger by unknown id returns 404`() {
        `when`(service.findById("x")).thenThrow(NotFoundException("Burger", "x"))

        mockMvc.get("/api/burgers/x") { header("X-API-Key", apiKey) }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `request without API key returns 401`() {
        mockMvc.get("/api/burgers")
            .andExpect { status { isUnauthorized() } }
    }

    @Test
    fun `POST burger with blank name returns 400`() {
        mockMvc.post("/api/burgers") {
            header("X-API-Key", apiKey)
            contentType = MediaType.APPLICATION_JSON
            content = """{"name":"","price":9.99,"weightInGrams":250}"""
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.error") { value("Validation Failed") }
        }
    }

    @Test
    fun `POST burger with negative price returns 400`() {
        mockMvc.post("/api/burgers") {
            header("X-API-Key", apiKey)
            contentType = MediaType.APPLICATION_JSON
            content = """{"name":"Burger","price":-1.00,"weightInGrams":250}"""
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `POST valid burger returns 201`() {
        `when`(service.create(any())).thenReturn(burger)

        mockMvc.post("/api/burgers") {
            header("X-API-Key", apiKey)
            contentType = MediaType.APPLICATION_JSON
            content = """{"name":"Ahoi Classic","price":9.99,"weightInGrams":250}"""
        }.andExpect {
            status { isCreated() }
            jsonPath("$.name") { value("Ahoi Classic") }
        }
    }
}

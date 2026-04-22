package com.ahoikapptn.ahoiburger.service

import com.ahoikapptn.ahoiburger.exception.NotFoundException
import com.ahoikapptn.ahoiburger.model.Burger
import com.ahoikapptn.ahoiburger.repository.BurgerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.mongodb.core.MongoTemplate
import java.math.BigDecimal
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class BurgerServiceTest {

    @Mock private lateinit var repository: BurgerRepository
    @Mock private lateinit var mongo: MongoTemplate
    @InjectMocks private lateinit var service: BurgerService

    private val burger = Burger(
        id = "1",
        name = "Ahoi Classic",
        price = BigDecimal("9.99"),
        weightInGrams = 250
    )

    @Test
    fun `findById returns burger when it exists`() {
        `when`(repository.findById("1")).thenReturn(Optional.of(burger))

        assertThat(service.findById("1")).isEqualTo(burger)
    }

    @Test
    fun `findById throws NotFoundException when burger does not exist`() {
        `when`(repository.findById("x")).thenReturn(Optional.empty())

        assertThrows<NotFoundException> { service.findById("x") }
    }

    @Test
    fun `create saves and returns the burger`() {
        `when`(repository.save(burger)).thenReturn(burger)

        assertThat(service.create(burger)).isEqualTo(burger)
        verify(repository).save(burger)
    }

    @Test
    fun `update throws NotFoundException when burger does not exist`() {
        `when`(repository.findById("x")).thenReturn(Optional.empty())

        assertThrows<NotFoundException> { service.update("x", burger) }
    }

    @Test
    fun `update saves burger with correct id`() {
        val updated = burger.copy(name = "Updated")
        `when`(repository.findById("1")).thenReturn(Optional.of(burger))
        `when`(repository.save(updated.copy(id = "1"))).thenReturn(updated)

        val result = service.update("1", updated)

        assertThat(result.name).isEqualTo("Updated")
    }

    @Test
    fun `delete throws NotFoundException when burger does not exist`() {
        `when`(repository.findById("x")).thenReturn(Optional.empty())

        assertThrows<NotFoundException> { service.delete("x") }
    }

    @Test
    fun `delete calls deleteById when burger exists`() {
        `when`(repository.findById("1")).thenReturn(Optional.of(burger))

        service.delete("1")

        verify(repository).deleteById("1")
    }
}

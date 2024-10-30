package org.example.sodam.domain.food.domain

import org.springframework.data.jpa.repository.JpaRepository

interface BasicFoodRepository : JpaRepository<BasicFood, Int> {
    fun findAllByIrdntContaining(irdnt: String): List<BasicFood>
}

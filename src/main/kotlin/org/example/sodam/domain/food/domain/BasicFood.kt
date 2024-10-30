package org.example.sodam.domain.food.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class BasicFood(
    @Id
    val id: Int,
    val koName: String,
    val summary: String,
    val cookingTime: String,
    val qnt: String,
    val nation_nm: String, // NATION_NM
    val irdnt: String
)

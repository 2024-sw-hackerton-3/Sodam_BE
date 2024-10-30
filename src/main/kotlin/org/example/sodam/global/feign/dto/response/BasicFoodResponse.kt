package org.example.sodam.global.feign.dto.response

data class BasicFoodResponse(
    val id: Int,
    val koName: String,
    val summary: String,
    val cookingTime: String,
    val qnt: String
)

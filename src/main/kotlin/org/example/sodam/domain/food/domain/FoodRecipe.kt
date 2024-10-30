package org.example.sodam.domain.food.domain

data class FoodRecipe(
    val danger: Boolean,
    val name: String,
    val qnt: String,
    val cookingTime: String,
    val substanList: List<String>,
    val substan: List<String>,
    val sauce: List<String>?,
    val step: List<String>
)

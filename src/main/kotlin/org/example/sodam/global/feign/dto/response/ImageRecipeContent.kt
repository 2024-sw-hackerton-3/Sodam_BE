package org.example.sodam.global.feign.dto.response

data class ImageRecipeContent(
    val danger: Boolean,
    val name: String,
    val substanList: List<String>,
    val substan: List<String>,
    val sauce: List<String>?,
    val step: List<String>,
    val cookingTime: String,
    val qnt: String,
    val image: String? = null
)

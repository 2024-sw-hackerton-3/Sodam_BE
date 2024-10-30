package org.example.sodam.global.feign.dto.response

data class FoodBasicResponse(
    val Grid_20150827000000000226_1: GridResponse
)

data class GridResponse(
    val totalCnt: Int,
    val startRow: Int,
    val endRow: Int,
    val result: Result,
    val row: List<Recipe>
)

data class Result(
    val code: String,
    val message: String
)

data class Recipe(
    val ROW_NUM: Int,
    val RECIPE_ID: Int,
    val RECIPE_NM_KO: String, // 레시피 이름(한글)
    val SUMRY: String, // 간략(요약) 소개
    val NATION_CODE: String,
    val NATION_NM: String,
    val TY_CODE: String,
    val TY_NM: String,
    val COOKING_TIME: String, // 조리시간
    val CALORIE: String,
    val QNT: String, // 분량
    val LEVEL_NM: String,
    val IRDNT_CODE: String,
    val PC_NM: String
)

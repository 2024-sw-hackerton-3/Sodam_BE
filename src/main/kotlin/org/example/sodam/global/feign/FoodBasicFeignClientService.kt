package org.example.sodam.global.feign

import com.google.gson.Gson
import org.example.sodam.domain.food.domain.BasicFood
import org.example.sodam.global.feign.dto.response.FoodBasicResponse
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class FoodBasicFeignClientService(
    private val foodFeignClient: FoodFeignClient
) {

    fun getFoodInfo(name: String?): List<BasicFood> {
        val start = Random.nextInt(1, 138)
        val end = minOf(start + 400, 537)

        val foodBasicInfo = foodFeignClient.basicInfo(
            start = start.toString(),
            end = end.toString(),
            koName = name
        )

        val foodBasicInfoJson = Gson().fromJson(foodBasicInfo, FoodBasicResponse::class.java)
        val foodBasicList = addToList(foodBasicInfoJson)
        return foodBasicList
    }

    private fun addToList(foodBasicListInfoJson: FoodBasicResponse): List<BasicFood> {
        val content = foodBasicListInfoJson.Grid_20150827000000000226_1.row ?: emptyList()
        return content.map { food ->
            BasicFood(
                id = food.RECIPE_ID,
                koName = food.RECIPE_NM_KO,
                summary = food.SUMRY,
                cookingTime = food.COOKING_TIME,
                qnt = food.QNT
            )
        }
    }
}

package org.example.sodam.global.feign

import org.example.sodam.global.feign.dto.response.BasicFoodResponse
import org.example.sodam.global.s3.FileUtil
import org.springframework.stereotype.Component
import org.example.sodam.domain.food.domain.BasicFood
import org.example.sodam.domain.food.domain.BasicFoodRepository

@Component
class FoodBasicFeignClientService(
    private val foodFeignClient: FoodFeignClient,
    private val fileUtil: FileUtil,
    private val basicFoodRepository: BasicFoodRepository
) {

    fun getFoodInfo(name: String?): List<BasicFoodResponse> {
//        val start = Random.nextInt(1, 138)
//        val end = minOf(start + 400, 537)
        val foods = if(name != null) basicFoodRepository.findAllByKoNameContains(name) else basicFoodRepository.findAll()

//        val foodBasicInfo = foodFeignClient.basicInfo(
//            start = start.toString(),
//            end = end.toString(),
//            koName = name
//        )

//        val foodBasicInfoJson = Gson().fromJson(foodBasicInfo, FoodBasicResponse::class.java)
        val foodBasicList = addToList(foods)
        return foodBasicList
    }

    private fun addToList(foodBasicListInfoJson: List<BasicFood>): List<BasicFoodResponse> {
//        val content = foodBasicListInfoJson.Grid_20150827000000000226_1.row ?: emptyList()

        return  foodBasicListInfoJson.map {  food ->
            val image = when (food.koName) {
                "비빔밥" -> fileUtil.generateObjectUrl("비빔밥.webp")
                else -> fileUtil.generateObjectUrl("기본.png")
            }
            BasicFoodResponse(
                id = food.id,
                koName = food.koName,
                summary = food.summary,
                cookingTime = food.cookingTime,
                qnt = food.qnt,
                image = image.substringBefore("?")
            )
        }
//        return content.map { food ->
//            val image = when (food.RECIPE_NM_KO) {
//                "비빔밥" -> fileUtil.generateObjectUrl("비빔밥.webp")
//                else -> fileUtil.generateObjectUrl("기본.png")
//            }
//            BasicFoodResponse(
//                id = food.RECIPE_ID,
//                koName = food.RECIPE_NM_KO,
//                summary = food.SUMRY,
//                cookingTime = food.COOKING_TIME,
//                qnt = food.QNT,
//                image = image.substringBefore("?")
//            )
//        }
    }
}

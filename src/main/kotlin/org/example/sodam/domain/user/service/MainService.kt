package org.example.sodam.domain.user.service

import org.example.sodam.domain.food.domain.BasicFoodRepository
import org.example.sodam.domain.user.enum.Food
import org.example.sodam.domain.user.exception.UserNotFoundException
import org.example.sodam.domain.user.facade.UserFacade
import org.example.sodam.global.feign.dto.response.BasicFoodResponse
import org.example.sodam.global.s3.FileUtil
import org.springframework.stereotype.Service

@Service
class MainService(
    private val userFacade: UserFacade,
    private val basicFoodRepository: BasicFoodRepository,
    private val fileUtil: FileUtil
) {
    fun main(): List<BasicFoodResponse> {
        val user = userFacade.getCurrentUser() ?: throw UserNotFoundException
        val keywords = user.foods?.split(",")

        val randomCategory = keywords?.random() ?: Food.entries.toTypedArray().random()

        println(randomCategory)
        val foods = basicFoodRepository.findAllByIrdntContaining(randomCategory.toString())

        return foods.map { food ->
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
        }.take(3)
    }
}

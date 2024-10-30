package org.example.sodam.domain.user.service

import org.example.sodam.domain.food.domain.BasicFoodRepository
import org.example.sodam.domain.user.enum.Food
import org.example.sodam.domain.user.exception.UserNotFoundException
import org.example.sodam.domain.user.facade.UserFacade
import org.example.sodam.global.feign.dto.response.BasicFoodResponse
import org.springframework.stereotype.Service

@Service
class MainService(
    private val userFacade: UserFacade,
    private val basicFoodRepository: BasicFoodRepository
) {
    fun main(): List<BasicFoodResponse> {
        val user = userFacade.getCurrentUser() ?: throw UserNotFoundException
        val keywords = user.foods?.split(",")

        val randomCategory = keywords?.random() ?: Food.entries.toTypedArray().random()

        println(randomCategory)
        val foods = basicFoodRepository.findAllByIrdntContaining(randomCategory.toString())

        return foods.map { food ->
            BasicFoodResponse(
                id = food.id,
                koName = food.koName,
                summary = food.summary,
                cookingTime = food.cookingTime,
                qnt = food.qnt
            )
        }.take(3)
    }
}

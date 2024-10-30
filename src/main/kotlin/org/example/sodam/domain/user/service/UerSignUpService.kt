package org.example.sodam.domain.user.service

import org.example.sodam.domain.user.domain.User
import org.example.sodam.domain.user.domain.UserRepository
import org.example.sodam.domain.user.enum.Food
import org.example.sodam.domain.user.exception.AlreadyExistIdException
import org.example.sodam.domain.user.exception.FoodNofFoundException
import org.example.sodam.domain.user.facade.UserFacade
import org.example.sodam.domain.user.presentation.dto.request.SignUpRequest
import org.example.sodam.global.jwt.JwtTokenProvider
import org.sodam.global.jwt.dto.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UerSignUpService(
    private val userFacade: UserFacade,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun signUp(request: SignUpRequest): TokenResponse {
        if (userFacade.checkAccountIdExist(request.accountId)) throw AlreadyExistIdException

        val foodEntries = request.foods?.map { foodName ->
            Food.entries.find { food -> food.subcategories.contains(foodName) }
        } ?: emptyList()

        foodEntries.forEach { food ->
            if (food == null) throw FoodNofFoundException
        }

        val categoryCount = mutableMapOf<Food, Int>()

        foodEntries.forEach { food ->
            categoryCount[food!!] = categoryCount.getOrDefault(food, 0) + 1
        }

        val topCategories = categoryCount.entries
            .sortedByDescending { it.value }
            .take(2)
            .map { it.key }

        val user = userRepository.save(
            User(
                name = request.name,
                accountId = request.accountId,
                password = passwordEncoder.encode(request.password),
                foods = topCategories.joinToString(separator = ",") { it.name }
            )
        )

        return jwtTokenProvider.getToken(user.accountId)
    }
}

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

        request.foods?.forEach { food ->
            if (Food.entries.none { it == food }) throw FoodNofFoundException
        }

        val user = userRepository.save(
            User(
                name = request.name,
                accountId = request.accountId,
                password = passwordEncoder.encode(request.password),
                foods = request.foods?.joinToString(separator = ",")
            )
        )
        return jwtTokenProvider.getToken(user.accountId)
    }
}

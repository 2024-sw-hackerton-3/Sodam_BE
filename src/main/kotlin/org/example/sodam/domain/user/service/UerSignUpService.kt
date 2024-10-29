package org.example.sodam.domain.user.service

import org.example.sodam.domain.user.domain.User
import org.example.sodam.domain.user.domain.UserRepository
import org.example.sodam.domain.user.exception.AlreadyExistIdException
import org.example.sodam.domain.user.facade.UserFacade
import org.example.sodam.domain.user.presentation.dto.request.SignRequest
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
    fun signUp(request: SignRequest): TokenResponse {
        if (userFacade.checkAccountIdExist(request.accountId)) throw AlreadyExistIdException

        val user = userRepository.save(
            User(
                accountId = request.accountId,
                password = passwordEncoder.encode(request.password)
            )
        )
        return jwtTokenProvider.getToken(user.accountId)
    }
}

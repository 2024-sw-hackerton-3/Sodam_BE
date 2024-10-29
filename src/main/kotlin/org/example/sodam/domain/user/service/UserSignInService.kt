package org.example.sodam.domain.user.service

import org.example.sodam.domain.user.domain.UserRepository
import org.example.sodam.domain.user.exception.PasswordMismatchException
import org.example.sodam.domain.user.facade.UserFacade
import org.example.sodam.domain.user.presentation.dto.request.SignInRequest
import org.example.sodam.global.jwt.JwtTokenProvider
import org.sodam.global.jwt.dto.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserSignInService(
    private val userFacade: UserFacade,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Transactional
    fun signIn(request: SignInRequest): TokenResponse {
        val user = userFacade.getByAccountId(request.accountId)

        if (!passwordEncoder.matches(request.password, user.password)) throw PasswordMismatchException

        return jwtTokenProvider.getToken(user.accountId)
    }
}

package org.example.sodam.domain.user.facade

import org.example.sodam.domain.user.domain.User
import org.example.sodam.domain.user.domain.UserRepository
import org.example.sodam.domain.user.exception.UserNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository
) {
    fun getCurrentUser(): User {
        val accountId = SecurityContextHolder.getContext().authentication.name
        return getByAccountId(accountId)
    }

    fun checkAccountIdExist(accountId: String): Boolean {
        return userRepository.existsByAccountId(accountId)
    }

    fun getByAccountId(schoolNumber: String): User {
        return userRepository.findByAccountId(schoolNumber) ?: throw UserNotFoundException
    }
}

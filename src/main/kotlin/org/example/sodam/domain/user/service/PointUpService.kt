package org.example.sodam.domain.user.service

import org.example.sodam.domain.user.exception.UserNotFoundException
import org.example.sodam.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointUpService(
    private val userFacade: UserFacade
) {
    @Transactional
    fun pointUp(point: Int) {
        val user = userFacade.getCurrentUser() ?: throw UserNotFoundException
        user.pointUp(point)
    }
}

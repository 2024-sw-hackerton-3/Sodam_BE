package org.example.sodam.domain.user.service

import org.example.sodam.domain.user.exception.UserNotFoundException
import org.example.sodam.domain.user.facade.UserFacade
import org.example.sodam.domain.user.presentation.dto.response.MyPageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MyPageService(
    private val userFacade: UserFacade
) {

    @Transactional(readOnly = true)
    fun myPage(): MyPageResponse {
        val user = userFacade.getCurrentUser() ?: throw UserNotFoundException

        return MyPageResponse(
            name = user.name,
            point = user.point
        )
    }
}

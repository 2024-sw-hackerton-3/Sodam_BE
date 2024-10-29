package org.example.sodam.domain.user.service

import org.example.sodam.domain.user.enum.Allergy
import org.example.sodam.domain.user.exception.AllergyNotFoundException
import org.example.sodam.domain.user.exception.UserNotFoundException
import org.example.sodam.domain.user.facade.UserFacade
import org.example.sodam.domain.user.presentation.dto.request.AllergyRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AllergyService(
    private val userFacade: UserFacade
) {

    @Transactional
    fun allergies(request: AllergyRequest) {
        val user = userFacade.getCurrentUser() ?: throw UserNotFoundException

        if (request.allergies.any { it == Allergy.NONE }) {
            user.updateAllergy(null)
            return
        }

        request.allergies.forEach { allergy ->
            if (Allergy.entries.none { it.koName == allergy.koName }) throw AllergyNotFoundException
        }

        user.updateAllergy(request.allergies)
    }
}

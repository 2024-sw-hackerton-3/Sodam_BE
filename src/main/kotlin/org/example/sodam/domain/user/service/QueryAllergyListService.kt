package org.example.sodam.domain.user.service

import org.example.sodam.domain.user.enum.Allergy
import org.example.sodam.domain.user.presentation.dto.response.QueryAllergyResponse
import org.springframework.stereotype.Service

@Service
class QueryAllergyListService {
    fun queryAllergyList() = Allergy.entries.map { QueryAllergyResponse(it.name, it.koName) }
}

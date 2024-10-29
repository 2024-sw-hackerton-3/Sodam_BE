package org.example.sodam.domain.user.presentation.dto.request

import org.example.sodam.domain.user.enum.Allergy

data class AllergyRequest(
    val allergies: List<Allergy>
)

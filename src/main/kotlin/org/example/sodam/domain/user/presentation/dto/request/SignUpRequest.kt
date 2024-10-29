package org.example.sodam.domain.user.presentation.dto.request

import org.example.sodam.domain.user.enum.Food

data class SignUpRequest(
    val name: String,
    val accountId: String,
    val password: String,
    val foods: List<Food>?
)

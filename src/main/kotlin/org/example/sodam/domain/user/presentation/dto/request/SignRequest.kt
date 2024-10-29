package org.example.sodam.domain.user.presentation.dto.request

data class SignRequest(
    val accountId: String,
    val password: String
)

package org.example.sodam.domain.user.presentation.dto.request

data class SignInRequest(
    val accountId: String,
    val password: String
)

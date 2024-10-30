package org.example.sodam.domain.user.presentation.dto.request

data class SignUpRequest(
    val name: String,
    val accountId: String,
    val password: String,
    val foods: List<String>?
)

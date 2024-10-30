package org.example.sodam.global.feign.dto.request

data class GPTRequest(
    val model: String,
    val messages: List<SendMessage>
)

data class SendMessage(
    val role: String,
    val content: String
)

package org.example.sodam.global.feign.dto.response

data class GPTResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
)

data class Choice(
    val index: Int,
    val message: Message,
    val logprobs: Any?,
    val finish_reason: String
)

data class Message(
    val role: String,
    val content: String,
    val refusal: Any?
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int,
    val prompt_tokens_details: PromptTokensDetails,
    val completion_tokens_details: CompletionTokensDetails
)

data class PromptTokensDetails(
    val cached_tokens: Int
)

data class CompletionTokensDetails(
    val reasoning_tokens: Int
)

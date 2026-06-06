package com.openkin.startaiprog.network.model.simplerequest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InteractionResponse(
    val created: String,
    val id: String,
    val model: String,
    val `object`: String, // object — ключевое слово в Kotlin, экранируем обратными кавычками
    val steps: List<Step>,
    val status: String,
    val updated: String,
    @SerialName("service_tier")
    val serviceTier: String,
    val usage: Usage,
)

@Serializable
data class Step(
    val type: String,
    val content: List<Content> = listOf(),
    val signature: String = "",
)

@Serializable
data class Content(
    val type: String,
    val text: String,
)

@Serializable
data class Usage(
    @SerialName("input_tokens_by_modality")
    val inputTokensByModality: List<ModalityToken>,
    @SerialName("total_cached_tokens")
    val totalCachedTokens: Int,
    @SerialName("total_input_tokens")
    val totalInputTokens: Int,
    @SerialName("total_output_tokens")
    val totalOutputTokens: Int,
    @SerialName("total_thought_tokens")
    val totalThoughtTokens: Int,
    @SerialName("total_tokens")
    val totalTokens: Int,
    @SerialName("total_tool_use_tokens")
    val totalToolUseTokens: Int,
)

@Serializable
data class ModalityToken(
    val modality: String,
    val tokens: Int,
)

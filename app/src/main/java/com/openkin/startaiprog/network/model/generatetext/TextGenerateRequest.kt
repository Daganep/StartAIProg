package com.openkin.startaiprog.network.model.generatetext

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TextGenerateRequest(
    @SerialName("contents")
    val contents: List<Parts>,
    @SerialName("generationConfig")
    val generationConfig: GenerationConfig,
)

@Serializable
data class Parts(
    @SerialName("parts")
    val parts: List<Part>
)

@Serializable
data class Part(
    @SerialName("text")
    val text: String,
)

@Serializable
data class GenerationConfig(
    @SerialName("maxOutputTokens")
    val maxOutputTokens: String,
    @SerialName("temperature")
    val temperature: String,
    @SerialName("topP")
    val topP: String,
    @SerialName("stopSequences")
    val stopSequences: List<String>,
)

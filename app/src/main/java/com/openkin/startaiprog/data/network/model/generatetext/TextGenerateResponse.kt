package com.openkin.startaiprog.data.network.model.generatetext

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TextGenerateResponse(
    @SerialName("candidates")
    val candidates: List<Candidate>,
    @SerialName("usageMetadata")
    val usageMetadata: UsageMetadata,
)

@Serializable
data class Candidate(
    @SerialName("content")
    val content: Parts,
    @SerialName("finishReason")
    val finishReason: String,
    @SerialName("index")
    val index: Int,
)

@Serializable
data class UsageMetadata(
    @SerialName("promptTokenCount")
    val promptTokenCount: Int,
    @SerialName("candidatesTokenCount")
    val candidatesTokenCount: Int,
    @SerialName("totalTokenCount")
    val totalTokenCount: Int,
    @SerialName("thoughtsTokenCount")
    val thoughtsTokenCount: Int,
)

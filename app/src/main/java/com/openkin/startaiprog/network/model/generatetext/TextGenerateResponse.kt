package com.openkin.startaiprog.network.model.generatetext

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TextGenerateResponse(
    @SerialName("candidates")
    val candidates: List<Candidate>,
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

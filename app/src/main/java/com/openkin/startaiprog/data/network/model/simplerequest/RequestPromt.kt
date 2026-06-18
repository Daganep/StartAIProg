package com.openkin.startaiprog.data.network.model.simplerequest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPromt(
    @SerialName("model")
    val model: String,
    @SerialName("input")
    val input: String,
)

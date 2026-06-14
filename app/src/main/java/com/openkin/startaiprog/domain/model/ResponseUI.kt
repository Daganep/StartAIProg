package com.openkin.startaiprog.domain.model

data class ResponseUI(
    val message: ChatMessageUI,
    val isError: Boolean,
    val totalTokensCount: String,
)

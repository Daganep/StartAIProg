package com.openkin.startaiprog.domain.model

data class ChatMessageUI(
    val messageId: Int,
    val message: String,
    val timestamp: Long,
    val outgoing: Boolean,
    val tokensCount: Int,
    val type: MessageType,
)

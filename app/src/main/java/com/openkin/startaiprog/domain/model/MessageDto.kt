package com.openkin.startaiprog.domain.model

data class MessageDto(
    val messageId: Int,
    val chatId: Int,
    val message: String,
    val timestamp: Long,
    val outgoing: Boolean,
    val tokensCount: Int,
    val type: MessageType,
)

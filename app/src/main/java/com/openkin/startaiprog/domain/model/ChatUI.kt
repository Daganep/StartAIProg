package com.openkin.startaiprog.domain.model

data class ChatUI(
    val chatId: Int,
    val chatName: String,
    val messages: List<ChatMessageUI>,
)

data class ChatMessageUI(
    val messageId: Int,
    val message: String,
    val messageTime: Long,
    val outgoing: Boolean,
)

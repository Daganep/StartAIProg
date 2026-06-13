package com.openkin.startaiprog.screen.chat.model

data class ChatUI(
    val chatId: String,
    val chatName: String,
    val messages: List<ChatMessageUI>,
)

data class ChatMessageUI(
    val messageId: String,
    val message: String,
    val messageTime: Long,
    val outgoing: Boolean,
)

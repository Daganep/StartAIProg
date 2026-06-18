package com.openkin.startaiprog.domain.model

data class ChatUI(
    val chatId: Int,
    val chatName: String,
    val messages: List<ChatMessageUI>,
)

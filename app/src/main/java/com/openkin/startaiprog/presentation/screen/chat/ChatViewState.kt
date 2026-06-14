package com.openkin.startaiprog.presentation.screen.chat

import com.openkin.startaiprog.domain.model.ChatMessageUI
import com.openkin.startaiprog.utils.EMPTY_STRING

data class ChatViewState(
    val chatName: String = EMPTY_STRING,
    val chatId: Int = -1,
    val promt: String = EMPTY_STRING,
    val response: String = EMPTY_STRING,
    val showPromt: Boolean = true,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val timerValue: Long = 0L,
    val totalTokens: String = EMPTY_STRING,
    val messages: List<ChatMessageUI> = listOf(),
)

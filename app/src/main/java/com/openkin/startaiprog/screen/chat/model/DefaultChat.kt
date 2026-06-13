package com.openkin.startaiprog.screen.chat.model

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object DefaultChat {

    private val _defaultChat = MutableStateFlow<ChatUI>(
        ChatUI(
            chatId = "100",
            chatName = "Чат без истории",
            messages = listOf(),
            )
    )
    val defaultChat: Flow<ChatUI> = _defaultChat.asStateFlow()

    fun addMessage(newMessage: ChatMessageUI) {
        Log.d("MyFilter", "addMessage. before: ${_defaultChat.value.messages.size}")
        val updatedMessages = _defaultChat.value.messages + newMessage
        Log.d("MyFilter", "addMessage. after: ${updatedMessages.size}")
        _defaultChat.update { it.copy(messages = updatedMessages) }
    }
}

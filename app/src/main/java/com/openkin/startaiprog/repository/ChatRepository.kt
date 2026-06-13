package com.openkin.startaiprog.repository

import com.openkin.startaiprog.screen.chat.model.ChatUI
import com.openkin.startaiprog.screen.chat.model.DefaultChat
import kotlinx.coroutines.flow.Flow

class ChatRepository() : IChatRepository {

    private val defaultChat: Flow<ChatUI> = DefaultChat.defaultChat

    override suspend fun loadChat(): Flow<ChatUI> {
        return defaultChat
    }
}

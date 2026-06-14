package com.openkin.startaiprog.domain

import com.openkin.startaiprog.data.model.ChatDbo
import com.openkin.startaiprog.data.model.MessageDbo
import kotlinx.coroutines.flow.Flow

interface IChatRepository {

    suspend fun loadChat(chatId: Int): Flow<List<MessageDbo>>

    suspend fun loadChats(): Flow<List<ChatDbo>>

    suspend fun getChatName(chatId: Int): Flow<String>

    suspend fun addChat(chatName: String): Flow<List<ChatDbo>>

    suspend fun removeChat(chatId: Int)
}

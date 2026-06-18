package com.openkin.startaiprog.domain

import com.openkin.startaiprog.data.model.ChatDbo
import com.openkin.startaiprog.domain.model.MessageDto
import kotlinx.coroutines.flow.Flow

interface IChatRepository {

    suspend fun loadMessages(chatId: Int): Flow<List<MessageDto>>

    suspend fun loadChats(): Flow<List<ChatDbo>>

    suspend fun getChat(chatId: Int): Flow<ChatDbo?>

    suspend fun addChat(chatName: String): Flow<List<ChatDbo>>

    suspend fun removeChat(chatId: Int)
}

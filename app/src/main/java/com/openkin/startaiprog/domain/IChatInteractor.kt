package com.openkin.startaiprog.domain

import com.openkin.startaiprog.data.model.ChatDbo
import com.openkin.startaiprog.domain.model.ChatMessageUI
import kotlinx.coroutines.flow.Flow

interface IChatInteractor {

    suspend fun loadMessages(chatId: Int): Flow<List<ChatMessageUI>>

    suspend fun loadChats(): Flow<List<ChatDbo>>

    suspend fun getChat(chatId: Int): Flow<ChatDbo?>

    suspend fun addChat(chatName: String): Flow<List<ChatDbo>>

    suspend fun removeChat(chatId: Int)
}

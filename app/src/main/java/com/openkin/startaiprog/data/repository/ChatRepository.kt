package com.openkin.startaiprog.data.repository

import com.openkin.startaiprog.data.database.ChatsDatabase
import com.openkin.startaiprog.data.model.ChatDbo
import com.openkin.startaiprog.domain.IChatRepository
import com.openkin.startaiprog.domain.model.MessageDto
import com.openkin.startaiprog.utils.EMPTY_STRING
import com.openkin.startaiprog.domain.mapper.messageDboToDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatRepository(
    private val dataBase: ChatsDatabase,
) : IChatRepository {

    override suspend fun loadMessages(chatId: Int): Flow<List<MessageDto>> =
        dataBase.messagesDao.getChatMessages(chatId).map { messagesDbo ->
            messagesDbo.map { messageDboToDto(it) }
        }

    override suspend fun loadChats(): Flow<List<ChatDbo>> {
        return dataBase.chatsDao.getAll()
    }

    override suspend fun getChat(chatId: Int): Flow<ChatDbo?> {
        return dataBase.chatsDao.getChat(chatId)
    }

    override suspend fun addChat(chatName: String): Flow<List<ChatDbo>> {
        val description = EMPTY_STRING
        val createDateMS = System.currentTimeMillis()
        val archived = false
        val newChat = ChatDbo(
            title = chatName,
            description = description,
            createDateMS = createDateMS,
            archived = archived,
            totalTokens = 0,
            chatSummary = EMPTY_STRING,
        )
        dataBase.chatsDao.insert(newChat)
        return dataBase.chatsDao.getAll()
    }

    override suspend fun removeChat(chatId: Int) {
        dataBase.chatsDao.remove(chatId)
    }
}

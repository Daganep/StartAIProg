package com.openkin.startaiprog.domain

import com.openkin.startaiprog.data.model.ChatDbo
import com.openkin.startaiprog.domain.model.ChatMessageUI
import com.openkin.startaiprog.domain.mapper.messageDtoToUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatInteractor(
    private val chatRepository: IChatRepository,
): IChatInteractor {

    override suspend fun loadMessages(chatId: Int): Flow<List<ChatMessageUI>> {
        return chatRepository.loadMessages(chatId).map { messagesDto ->
            messagesDto.map { messageDtoToUi(it) }
        }
    }

    override suspend fun loadChats(): Flow<List<ChatDbo>> = chatRepository.loadChats()

    override suspend fun getChat(chatId: Int): Flow<ChatDbo?> = chatRepository.getChat(chatId)

    override suspend fun addChat(chatName: String): Flow<List<ChatDbo>> {
        return chatRepository.addChat(chatName)
    }

    override suspend fun removeChat(chatId: Int) {
        chatRepository.removeChat(chatId)
    }
}

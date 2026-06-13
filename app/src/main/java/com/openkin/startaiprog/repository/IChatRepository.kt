package com.openkin.startaiprog.repository

import com.openkin.startaiprog.screen.chat.model.ChatUI
import kotlinx.coroutines.flow.Flow

interface IChatRepository {

    suspend fun loadChat(): Flow<ChatUI>
}

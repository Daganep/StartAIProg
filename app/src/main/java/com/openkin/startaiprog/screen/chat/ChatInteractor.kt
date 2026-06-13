package com.openkin.startaiprog.screen.chat

import com.openkin.startaiprog.screen.chat.model.ChatUI

class ChatInteractor: IChatInteractor {

    override suspend fun loadChat(): ChatUI {
        return ChatUI("", "", listOf())
    }
}

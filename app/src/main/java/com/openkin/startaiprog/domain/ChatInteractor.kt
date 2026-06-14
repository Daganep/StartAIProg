package com.openkin.startaiprog.domain

import com.openkin.startaiprog.domain.model.ChatUI

class ChatInteractor: IChatInteractor {

    override suspend fun loadChat(): ChatUI {
        return ChatUI(200, "", listOf())
    }
}

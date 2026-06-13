package com.openkin.startaiprog.screen.chat

import com.openkin.startaiprog.screen.chat.model.ChatUI

interface IChatInteractor {

    suspend fun loadChat(): ChatUI
}

package com.openkin.startaiprog.screen.mainscreen.model

import com.openkin.startaiprog.screen.chat.model.ChatMessageUI

data class ResponseUI(
    val message: ChatMessageUI,
    val isError: Boolean,
    val totalTokensCount: String,
)

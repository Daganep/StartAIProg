package com.openkin.startaiprog.network.model

data class MessageDTO(
    val messageId: String,
    val message: String,
    val messageTime: Long,
    val outgoing: Boolean,
)

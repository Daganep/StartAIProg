package com.openkin.startaiprog.data.network.model

data class MessageDTO(
    val messageId: String,
    val message: String,
    val messageTime: Long,
    val outgoing: Boolean,
)

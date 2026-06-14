package com.openkin.startaiprog.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class ChatWithMessages(
    @Embedded val chat: ChatDbo,

    @Relation(
        parentColumn = "chatId",   // Поле 'chatId' из ChatDbo
        entityColumn = "chatId"    // Поле 'chatId' из MessageDbo
    )
    val messages: List<MessageDbo>
)

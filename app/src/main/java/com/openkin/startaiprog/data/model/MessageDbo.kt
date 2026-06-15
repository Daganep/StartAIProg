package com.openkin.startaiprog.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_messages_database")
data class MessageDbo(
    @PrimaryKey(autoGenerate = true) val messageId: Int = 0,
    @ColumnInfo("chatId") val chatId: Int,
    @ColumnInfo("message") val message: String,
    @ColumnInfo("timestamp") val timestamp: Long,
    @ColumnInfo("outgoing") val outgoing: Boolean,
    @ColumnInfo("tokensCount") val tokensCount: Int,
)

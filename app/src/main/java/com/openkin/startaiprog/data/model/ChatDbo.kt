package com.openkin.startaiprog.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_chats_database")
data class ChatDbo(
    @PrimaryKey(autoGenerate = true) val chatId: Int = 0,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("createDateMS") val createDateMS: Long,
    @ColumnInfo("archived") val archived: Boolean,
)

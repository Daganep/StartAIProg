package com.openkin.startaiprog.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openkin.startaiprog.data.model.ChatDbo
import com.openkin.startaiprog.data.model.ChatWithMessages
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatsDao {

    @Query("SELECT * FROM table_chats_database")
    fun getAll(): Flow<List<ChatDbo>>

    @Query("SELECT * FROM table_chats_database WHERE chatId = :chatId")
    suspend fun getChatWithMessages(chatId: Int): ChatWithMessages?

    @Query("SELECT title FROM table_chats_database WHERE chatId = :chatId")
    suspend fun getChatName(chatId: Int): Flow<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chat: ChatDbo)

    @Query("DELETE FROM table_chats_database WHERE chatId = :chatId")
    suspend fun remove(chatId: Int)
}

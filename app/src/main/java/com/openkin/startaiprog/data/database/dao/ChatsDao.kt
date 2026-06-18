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
    fun getChat(chatId: Int): Flow<ChatDbo?>

    @Query("SELECT * FROM table_chats_database WHERE chatId = :chatId")
    suspend fun getChatWithMessages(chatId: Int): ChatWithMessages?

    @Query("SELECT title FROM table_chats_database WHERE chatId = :chatId")
    suspend fun getChatName(chatId: Int): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chat: ChatDbo)

    @Query("UPDATE table_chats_database SET totalTokens = :totalTokens WHERE chatId = :chatId")
    suspend fun updateTotalTokens(chatId: Int, totalTokens: Int)

    @Query("UPDATE table_chats_database SET chatSummary = :summary WHERE chatId = :chatId")
    suspend fun updateChatSummary(chatId: Int, summary: String)

    @Query("SELECT chatSummary FROM table_chats_database WHERE chatId = :chatId")
    fun getChatSummary(chatId: Int): Flow<String>

    @Query("DELETE FROM table_chats_database WHERE chatId = :chatId")
    suspend fun remove(chatId: Int)
}

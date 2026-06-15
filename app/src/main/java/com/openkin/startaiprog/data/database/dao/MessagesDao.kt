package com.openkin.startaiprog.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openkin.startaiprog.data.model.MessageDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {

    @Query("SELECT * FROM table_messages_database WHERE chatId = :chatId")
    fun getChatMessages(chatId: Int): Flow<List<MessageDbo>>

    @Query("UPDATE table_messages_database SET tokensCount = :tokensCount WHERE messageId = " +
            "(SELECT messageId FROM table_messages_database ORDER BY timestamp DESC LIMIT 1)")
    fun updateLastMessageTokens(tokensCount: Int)

    @Query("SELECT COUNT(*) FROM table_messages_database")
    suspend fun getSize(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: MessageDbo)
}

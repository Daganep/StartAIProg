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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: MessageDbo)
}

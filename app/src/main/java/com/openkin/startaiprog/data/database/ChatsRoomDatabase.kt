package com.openkin.startaiprog.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openkin.startaiprog.data.database.dao.ChatsDao
import com.openkin.startaiprog.data.database.dao.MessagesDao
import com.openkin.startaiprog.data.model.ChatDbo
import com.openkin.startaiprog.data.model.MessageDbo

/**
 * [ChatsDatabase] — это класс обертка для того чтобы не прокидывать зависимость библиотеки Room
 * в модуль, в котором будет реализовано взаимодействие с базой данных
 */
class ChatsDatabase internal constructor(private val database: ChatsRoomDatabase) {
    val chatsDao: ChatsDao
        get() = database.chatsDao()
    val messagesDao: MessagesDao
        get() = database.messagesDao()
}

@Database(entities = [ChatDbo::class, MessageDbo::class], version = 1, exportSchema = false)
abstract class ChatsRoomDatabase : RoomDatabase() {
    abstract fun chatsDao(): ChatsDao
    abstract fun messagesDao(): MessagesDao
}

fun chatsDatabase(applicationContext: Context): ChatsDatabase {
    val database = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        ChatsRoomDatabase::class.java, "chats_database"
    ).build()
    return ChatsDatabase(database)
}

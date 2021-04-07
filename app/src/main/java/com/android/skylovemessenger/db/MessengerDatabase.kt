package com.android.skylovemessenger.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.skylovemessenger.db.daos.ChatDao
import com.android.skylovemessenger.db.daos.DeletedMessageDao
import com.android.skylovemessenger.db.daos.MessageDao
import com.android.skylovemessenger.db.daos.UserDao
import com.android.skylovemessenger.db.entities.Chat
import com.android.skylovemessenger.db.entities.DeletedMessage
import com.android.skylovemessenger.db.entities.Message
import com.android.skylovemessenger.db.entities.User


@Database(entities = [User::class, Chat::class, Message::class, DeletedMessage::class], version = 1)
@TypeConverters(Converters::class)
abstract class MessengerDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun deletedMessageDao(): DeletedMessageDao

    companion object {
        private val DB_NAME = "messenger_database.db"

        @Volatile
        private var instance: MessengerDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MessengerDatabase? {
            if (instance == null) {
                instance = create(context)
            }
            return instance
        }

        private fun create(context: Context): MessengerDatabase {
            return Room.databaseBuilder(
                context,
                MessengerDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}
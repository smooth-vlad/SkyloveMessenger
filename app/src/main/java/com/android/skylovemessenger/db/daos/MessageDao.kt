package com.android.skylovemessenger.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import com.android.skylovemessenger.db.entities.Message
import com.android.skylovemessenger.db.entities.User
import java.time.LocalDateTime

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAll(): List<Message>

    @Query("SELECT * FROM message WHERE chatId = :chatId")
    fun getAllFor(chatId: Long): List<MessageDescription>

    @Insert
    fun insert(message: Message)
}

data class MessageDescription(
    val messageId: Long,
    val authorId: Long,
    @Relation(parentColumn = "authorId", entityColumn = "userId")
    val author: User,
    val dateTime: LocalDateTime,
    val isDeletedForAuthor: Boolean,
    val text: String
)
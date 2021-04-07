package com.android.skylovemessenger.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.skylovemessenger.db.entities.Message
import com.android.skylovemessenger.db.entities.User
import java.time.LocalDateTime

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAll(): LiveData<List<Message>>

    @Transaction
    @Query(
        """
        SELECT M.messageId, authorId, dateTime, chatId, text FROM 
            (SELECT *
            FROM message
            WHERE chatId = :chatId) M LEFT OUTER JOIN (
                SELECT *
                FROM deletedmessage
                WHERE forUserId = :userId) DM ON DM.messageId = M.messageId
                WHERE DM.deletedMessageId IS NULL
        """
    )
    fun getAllFor(chatId: Long, userId: Long): LiveData<List<MessageDescription>>

    @Insert
    suspend fun insert(message: Message)

    @Query("DELETE FROM message WHERE messageId = :messageId")
    suspend fun delete(messageId: Long)
}

data class MessageDescription(
    val messageId: Long,
    val authorId: Long,
    @Relation(parentColumn = "authorId", entityColumn = "userId")
    val author: User,
    val dateTime: LocalDateTime,
    val text: String
)
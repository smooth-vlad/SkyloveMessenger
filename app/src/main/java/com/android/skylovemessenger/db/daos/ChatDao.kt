package com.android.skylovemessenger.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import com.android.skylovemessenger.db.entities.Chat
import com.android.skylovemessenger.db.entities.Message
import com.android.skylovemessenger.db.entities.User

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat")
    fun getAll(): List<Chat>

    @Query("""
        SELECT CD.chatId, userId, messageId as lastMessageId
        FROM (
            SELECT
                chatId,
                CASE
                    WHEN user1Id = :userId THEN user2Id
                    WHEN user2Id = :userId THEN user1Id
                END AS userId
            FROM chat
            WHERE user1Id = :userId OR user2Id = :userId ) CD LEFT JOIN (
                SELECT * FROM message
                ORDER BY dateTime DESC
                LIMIT 1) M ON CD.chatId = M.chatId
        """)
    fun getAllDescriptionsFor(userId: Int): List<ChatDescription>

    @Insert
    fun insert(chat: Chat)
}

data class ChatDescription(
    val chatId: Long,
    val userId: Long,
    @Relation(parentColumn = "userId", entityColumn = "userId")
    val user: User,
    val lastMessageId: Long,
    @Relation(parentColumn = "lastMessageId", entityColumn = "messageId")
    val message: Message?,
)
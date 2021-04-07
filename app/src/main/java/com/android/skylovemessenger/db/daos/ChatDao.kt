package com.android.skylovemessenger.db.daos

import androidx.lifecycle.LiveData
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
    fun getAll(): LiveData<List<Chat>>

    @Query(
        """
        SELECT CD.chatId, userId, MAX(messageId) as lastMessageId
        FROM (
            SELECT
            chatId,
            CASE
                WHEN user1Id = :userId THEN user2Id
                WHEN user2Id = :userId THEN user1Id
            END AS userId
            FROM chat
            WHERE user1Id = :userId OR user2Id = :userId) CD LEFT JOIN (
                SELECT * FROM message LEFT OUTER JOIN (
                    SELECT * FROM deletedmessage
                    WHERE forUserId = :userId
                ) DM ON message.messageId = DM.messageId
                WHERE DM.messageId IS NULL) M ON CD.chatId = M.chatId 
        GROUP BY CD.chatId
        """
    )
    fun getAllDescriptionsFor(userId: Long): LiveData<List<ChatDescription>>

    @Insert
    suspend fun insert(chat: Chat)
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
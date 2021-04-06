package com.android.skylovemessenger.db

import androidx.room.Embedded
import androidx.room.Relation
import com.android.skylovemessenger.db.entities.Chat
import com.android.skylovemessenger.db.entities.Message
import com.android.skylovemessenger.db.entities.User

data class ChatDescription(
    val userId: Long,
    @Relation(parentColumn = "userId", entityColumn = "userId")
    val user: User,
    val lastMessageId: Long,
    @Relation(parentColumn = "lastMessageId", entityColumn = "messageId")
    val message: Message?,
)
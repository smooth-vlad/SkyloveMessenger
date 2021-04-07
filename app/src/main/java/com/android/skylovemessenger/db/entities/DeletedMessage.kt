package com.android.skylovemessenger.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeletedMessage(
    @PrimaryKey(autoGenerate = true) val deletedMessageId: Long,
    val messageId: Long,
    val forUserId: Long
)

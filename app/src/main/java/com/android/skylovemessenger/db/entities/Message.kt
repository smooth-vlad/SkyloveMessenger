package com.android.skylovemessenger.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val messageId: Long,
    val authorId: Long,
    val dateTime: LocalDateTime,
    val chatId: Long,
    val isDeletedForAuthor: Boolean,
    val text: String
)
package com.android.skylovemessenger.db.entities

import androidx.room.*

@Entity
data class Chat(
    @PrimaryKey(autoGenerate = true) val chatId: Long,
    val user1Id: Long,
    val user2Id: Long,
)
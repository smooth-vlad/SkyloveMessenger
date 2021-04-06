package com.android.skylovemessenger.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.skylovemessenger.db.entities.Chat

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat")
    fun getAll(): List<Chat>

    @Insert
    fun insert(chat: Chat)
}
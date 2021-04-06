package com.android.skylovemessenger.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.skylovemessenger.db.entities.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAll(): List<Message>

    @Insert
    fun insert(message: Message)
}
package com.android.skylovemessenger.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.skylovemessenger.db.entities.DeletedMessage

@Dao
interface DeletedMessageDao {
    @Query("SELECT * FROM deletedmessage")
    fun getAll(): LiveData<List<DeletedMessage>>

    @Insert
    suspend fun insert(deletedMessage: DeletedMessage)
}
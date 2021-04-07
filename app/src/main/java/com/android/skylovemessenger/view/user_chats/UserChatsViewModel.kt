package com.android.skylovemessenger.view.user_chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.skylovemessenger.db.MessengerDatabase
import java.lang.IllegalArgumentException

class UserChatsViewModel(private val currentUserId: Int, private val db: MessengerDatabase) :
    ViewModel() {
    val descriptions = db.chatDao().getAllDescriptionsFor(currentUserId)
}

class UserChatsViewModelFactory(private val currentUserId: Int, private val db: MessengerDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserChatsViewModel::class.java))
            return UserChatsViewModel(currentUserId, db) as T
        throw IllegalArgumentException()
    }

}
package com.android.skylovemessenger.view.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.skylovemessenger.db.MessengerDatabase
import java.lang.IllegalArgumentException

class ChatViewModel(private val currentUserId: Long, private val chatId: Long, private val db: MessengerDatabase) : ViewModel() {
    val messages = db.messageDao().getAllFor(chatId)
}

class ChatViewModelFactory(private val currentUserId: Long, private val chatId: Long, private val db: MessengerDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java))
            return ChatViewModel(currentUserId, chatId, db) as T
        throw IllegalArgumentException()
    }

}
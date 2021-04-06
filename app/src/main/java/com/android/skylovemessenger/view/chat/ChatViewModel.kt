package com.android.skylovemessenger.view.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.skylovemessenger.db.MessengerDatabase
import com.android.skylovemessenger.db.entities.Message
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

class ChatViewModel(
    private val currentUserId: Long,
    private val chatId: Long,
    private val db: MessengerDatabase
) : ViewModel() {
    val messages = db.messageDao().getAllFor(chatId)

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(text: String) {
        viewModelScope.launch {
            db.messageDao()
                .insert(Message(0, currentUserId, LocalDateTime.now(), chatId, false, text))
        }
    }
}

class ChatViewModelFactory(
    private val currentUserId: Long,
    private val chatId: Long,
    private val db: MessengerDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java))
            return ChatViewModel(currentUserId, chatId, db) as T
        throw IllegalArgumentException()
    }

}
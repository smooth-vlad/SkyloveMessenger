package com.android.skylovemessenger.view.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.*
import com.android.skylovemessenger.BR
import com.android.skylovemessenger.db.MessengerDatabase
import com.android.skylovemessenger.db.entities.Message
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

class ChatViewModel(
    private val currentUserId: Long,
    private val chatId: Long,
    private val db: MessengerDatabase
) : ViewModel(), Observable {
    val messages = db.messageDao().getAllFor(chatId)

    @get:Bindable
    var messageText = "hey"
        set(value) {
            field = value

            notifyChange(BR.messageText)
        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage() {
        viewModelScope.launch {
            db.messageDao()
                .insert(Message(0, currentUserId, LocalDateTime.now(), chatId, false, messageText))
        }
    }

    @delegate:Transient
    private val callBacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callBacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callBacks.remove(callback)
    }

    fun notifyChange() {
        callBacks.notifyChange(this, 0)
    }

    fun notifyChange(viewId:Int){
        callBacks.notifyChange(this, viewId)
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
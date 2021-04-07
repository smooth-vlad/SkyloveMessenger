package com.android.skylovemessenger.view.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.android.skylovemessenger.db.MessengerDatabase
import com.android.skylovemessenger.db.entities.Chat
import com.android.skylovemessenger.db.entities.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthViewModel(private val db: MessengerDatabase): ViewModel() {
    val users = db.userDao().getAll()

    fun create3Users() {
        viewModelScope.launch {
            db.userDao().insert(User(0, "vladislav", null))
            db.userDao().insert(User(0, "van4el", null))
            db.userDao().insert(User(0, "nikita_b", null))
        }
    }
}

class AuthViewModelFactory(
    private val db: MessengerDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java))
            return AuthViewModel(db) as T
        throw IllegalArgumentException()
    }

}
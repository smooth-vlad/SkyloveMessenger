package com.android.skylovemessenger.view.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.skylovemessenger.db.MessengerDatabase

class AuthViewModel(private val db: MessengerDatabase): ViewModel() {
    val users = db.userDao().getAll()
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
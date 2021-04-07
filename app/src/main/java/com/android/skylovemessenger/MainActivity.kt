package com.android.skylovemessenger

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.android.skylovemessenger.db.MessengerDatabase
import com.android.skylovemessenger.db.entities.Chat
import com.android.skylovemessenger.db.entities.Message
import com.android.skylovemessenger.db.entities.User
import com.android.skylovemessenger.view.user_chats.UserChatsFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
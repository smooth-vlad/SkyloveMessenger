package com.android.skylovemessenger.view.chat

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.skylovemessenger.R
import com.android.skylovemessenger.db.MessengerDatabase
import kotlinx.coroutines.launch

private const val TAG = "ChatFragment"

class ChatFragment : Fragment() {

    private var columnCount = 1

    private lateinit var viewModel: ChatViewModel

    private val args: ChatFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_fragment, container, false)

        val rv = view.findViewById(R.id.messages_list) as RecyclerView
        val sendButton = view.findViewById(R.id.send_button) as Button
        val messageText = view.findViewById(R.id.input_message_text) as EditText

        val db = MessengerDatabase.getInstance(requireContext())!!
        val viewModelFactory = ChatViewModelFactory(1, args.chatId, db)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChatViewModel::class.java)

        viewModel.messages.observe(viewLifecycleOwner) {
            with(rv) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = ChatRecyclerViewAdapter(it)
            }
        }

        sendButton.setOnClickListener {
            viewModel.sendMessage(messageText.text.toString())
        }

        return view
    }
}
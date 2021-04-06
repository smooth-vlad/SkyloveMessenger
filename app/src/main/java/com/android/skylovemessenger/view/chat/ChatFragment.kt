package com.android.skylovemessenger.view.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.skylovemessenger.R
import com.android.skylovemessenger.db.MessengerDatabase
import com.android.skylovemessenger.view.user_chats.UserChatsViewModel
import com.android.skylovemessenger.view.user_chats.UserChatsViewModelFactory

private const val TAG = "ChatFragment"

class ChatFragment : Fragment() {

    private var columnCount = 1

    private lateinit var viewModel: ChatViewModel

    private val args: ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_fragment, container, false)

        val rv = view.findViewById(R.id.messages_list) as RecyclerView

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

        return view
    }
}
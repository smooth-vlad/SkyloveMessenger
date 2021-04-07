package com.android.skylovemessenger.view.chat

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.skylovemessenger.R
import com.android.skylovemessenger.databinding.FragmentChatBinding
import com.android.skylovemessenger.db.MessengerDatabase

private const val TAG = "ChatFragment"

class ChatFragment : Fragment() {

    private var columnCount = 1

    private lateinit var viewModel: ChatViewModel

    private val args: ChatFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentChatBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)

        val rv = binding.messagesList

        val db = MessengerDatabase.getInstance(requireContext())!!
        val viewModelFactory = ChatViewModelFactory(args.currentUserId, args.chatId, db)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ChatViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.messages.observe(viewLifecycleOwner) {
            with(rv) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = ChatRecyclerViewAdapter(it)
            }
        }

        return binding.root
    }
}
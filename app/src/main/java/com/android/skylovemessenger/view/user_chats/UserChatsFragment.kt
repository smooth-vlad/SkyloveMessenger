package com.android.skylovemessenger.view.user_chats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.skylovemessenger.R
import com.android.skylovemessenger.databinding.FragmentUserChatsBinding
import com.android.skylovemessenger.db.MessengerDatabase
import com.android.skylovemessenger.db.entities.Chat
import com.android.skylovemessenger.view.chat.ChatFragmentArgs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "UserChatsFragment"

class UserChatsFragment : Fragment(), UserChatsRecyclerViewAdapter.OnChatClickListener {

    lateinit var viewModel: UserChatsViewModel

    private val args: UserChatsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentUserChatsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_chats, container, false)

        val rv = binding.chatsList

        val db = MessengerDatabase.getInstance(requireContext())!!
        val viewModelFactory = UserChatsViewModelFactory(args.currentUserId, db)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserChatsViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.descriptions.observe(viewLifecycleOwner) {
            rv.layoutManager = LinearLayoutManager(context)
            rv.adapter = UserChatsRecyclerViewAdapter(it, this)
        }

        db.chatDao().getChatsFor(viewModel.currentUserId).observe(viewLifecycleOwner) { chatsOtherUsersIds ->
            db.userDao().getAll().observe(viewLifecycleOwner) { users ->
                GlobalScope.launch {
                    for (user in users) {
                        if (user.userId != viewModel.currentUserId
                            && !chatsOtherUsersIds.contains(user.userId)) {
                            db.chatDao().insert(Chat(0, viewModel.currentUserId, user.userId))
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onChatClick(position: Int) {
        val chatId = viewModel.descriptions.value!![position].chatId
        val action = UserChatsFragmentDirections.actionUserChatsFragmentToChatFragment(
            chatId,
            viewModel.currentUserId
        )
        findNavController().navigate(action)
    }
}
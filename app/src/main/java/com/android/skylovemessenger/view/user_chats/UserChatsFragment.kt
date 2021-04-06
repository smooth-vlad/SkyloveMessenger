package com.android.skylovemessenger.view.user_chats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.skylovemessenger.R
import com.android.skylovemessenger.db.MessengerDatabase
import com.android.skylovemessenger.db.daos.ChatDescription

private const val TAG = "UserChatsFragment"

class UserChatsFragment : Fragment(), UserChatsRecyclerViewAdapter.OnChatClickListener {

    val viewModel: UserChatsViewModel by viewModels()

    private var columnCount = 1

    private val currentUserId = 1
    private var descriptions: List<ChatDescription>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_chats_fragment, container, false)

        val db = MessengerDatabase.getInstance(requireContext())
        db?.let {
            descriptions = it.chatDao().getAllDescriptionsFor(currentUserId)

//          Set the adapter
            val rv = view.findViewById(R.id.list) as RecyclerView
            rv.layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            rv.adapter = UserChatsRecyclerViewAdapter(descriptions!!, this)
        }
        return view
    }

    override fun onChatClick(position: Int) {
        val chatId = descriptions!![position].chatId
        val action = UserChatsFragmentDirections.actionUserChatsFragmentToChatFragment(chatId)
        findNavController().navigate(action)
    }
}
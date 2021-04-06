package com.android.skylovemessenger.view.user_chats

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.skylovemessenger.R
import com.android.skylovemessenger.db.MessengerDatabase
import com.android.skylovemessenger.db.daos.ChatDescription

private const val TAG = "UserChatsFragment"

class UserChatsFragment : Fragment(), UserChatsRecyclerViewAdapter.OnChatClickListener {

    lateinit var viewModel: UserChatsViewModel

    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_chats_fragment, container, false)

        val rv = view.findViewById(R.id.chats_list) as RecyclerView

        val db = MessengerDatabase.getInstance(requireContext())!!
        val viewModelFactory = UserChatsViewModelFactory(1, db)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserChatsViewModel::class.java)

        viewModel.descriptions.observe(viewLifecycleOwner) {
            rv.layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            rv.adapter = UserChatsRecyclerViewAdapter(it, this)
        }

        return view
    }

    override fun onChatClick(position: Int) {
        val chatId = viewModel.descriptions.value!![position].chatId
        val action = UserChatsFragmentDirections.actionUserChatsFragmentToChatFragment(chatId)
        findNavController().navigate(action)
    }
}
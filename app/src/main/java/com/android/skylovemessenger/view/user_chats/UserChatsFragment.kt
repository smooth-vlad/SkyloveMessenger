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
import com.android.skylovemessenger.R
import com.android.skylovemessenger.db.MessengerDatabase

class UserChatsFragment : Fragment() {

    val viewModel: UserChatsViewModel by viewModels()

    private var columnCount = 1

    private val currentUserId = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_chats_fragment, container, false)

        val db = MessengerDatabase.getInstance(requireContext())
        db?.let {
            val descriptions = it.chatDao().getAllDescriptionsFor(currentUserId)

//          Set the adapter
            with(view.findViewById(R.id.list) as RecyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = UserChatsRecyclerViewAdapter(descriptions)
            }
        }
        return view
    }
}
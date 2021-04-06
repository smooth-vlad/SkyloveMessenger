package com.android.skylovemessenger.view.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.android.skylovemessenger.R
import com.android.skylovemessenger.view.chat.dummy.DummyContent

private const val TAG = "ChatFragment"

class ChatFragment : Fragment() {

    private var columnCount = 1
    private var chatId: Long = 0

    private val args: ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_fragment, container, false)

        chatId = args.chatId

        Toast.makeText(context, "$chatId", Toast.LENGTH_SHORT).show()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = ChatRecyclerViewAdapter(DummyContent.ITEMS)
            }
        }
        return view
    }
}
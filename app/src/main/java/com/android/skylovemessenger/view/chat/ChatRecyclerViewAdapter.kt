package com.android.skylovemessenger.view.chat

import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.android.skylovemessenger.R
import com.android.skylovemessenger.db.daos.MessageDescription
import com.android.skylovemessenger.db.entities.Message
import java.time.format.DateTimeFormatter


class ChatRecyclerViewAdapter(
    private val values: List<MessageDescription>,
    private val thisUserId: Long
) : RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_message_item, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        when (item.authorId) {
            thisUserId -> {
                holder.tut.text = item.text
                holder.tud.text = item.dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                holder.otherUserMessage.visibility = View.GONE
            }
            else -> {
                holder.out.text = item.text
                holder.oud.text = item.dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                holder.thisUserMessage.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tut: TextView = view.findViewById(R.id.this_user_text)
        val tud: TextView = view.findViewById(R.id.this_user_date)
        val thisUserMessage: View = view.findViewById(R.id.this_user_message)
        val out: TextView = view.findViewById(R.id.other_user_text)
        val oud: TextView = view.findViewById(R.id.other_user_date)
        val otherUserMessage: View = view.findViewById(R.id.other_user_message)
    }
}
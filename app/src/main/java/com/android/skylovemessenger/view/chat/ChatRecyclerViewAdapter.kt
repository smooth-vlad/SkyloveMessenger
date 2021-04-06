package com.android.skylovemessenger.view.chat

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.skylovemessenger.R
import com.android.skylovemessenger.db.daos.MessageDescription
import com.android.skylovemessenger.db.entities.Message


class ChatRecyclerViewAdapter(
    private val values: List<MessageDescription>
) : RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_message_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.author.name
        holder.contentView.text = item.text
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
    }
}
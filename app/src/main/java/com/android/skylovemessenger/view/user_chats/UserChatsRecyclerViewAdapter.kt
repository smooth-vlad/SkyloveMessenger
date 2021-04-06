package com.android.skylovemessenger.view.user_chats

import android.media.Image
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.android.skylovemessenger.R
import com.android.skylovemessenger.db.ChatDescription

import java.time.format.DateTimeFormatter

class UserChatsRecyclerViewAdapter(
    private val values: List<ChatDescription>
) : RecyclerView.Adapter<UserChatsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_item, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.name.text = item.user.name
        if (item.message != null) {
            holder.lastMessageText.text = item.message.text
            holder.date.text = item.message.dateTime.format(DateTimeFormatter.ofPattern("hh:mm"))
        }
        else {
            holder.lastMessageText.text = "Начните беседу первым!"
            holder.date.text = ""
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.user)
        val photo: ImageView = view.findViewById(R.id.photo)
        val lastMessageText: TextView = view.findViewById(R.id.text)
        val date: TextView = view.findViewById(R.id.date)
    }
}
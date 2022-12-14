package com.sounge.soungeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.Notificacao
import com.sounge.soungeapp.enums.NotificationType

class NotificationAdapter(val notifications: MutableList<Notificacao>) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(notificacao: Notificacao) {
            itemView.findViewById<TextView>(R.id.text_message).text = notificacao.type.message
            itemView.findViewById<TextView>(R.id.text_title).text = notificacao.type.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return NotificationViewHolder(view)

    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount(): Int = notifications.size

}

private val NotificationType.title: String
    get() = when (this) {
        NotificationType.MATCH -> "Sintonize-se"
        NotificationType.COMMENT -> "Comentário"
        NotificationType.LIKE -> "Curtida"
    }

private val NotificationType.message: String
    get() = when (this) {
        NotificationType.MATCH -> "Fulando sintonizou com você!"
        NotificationType.COMMENT -> "Fulano comentou no seu post!"
        NotificationType.LIKE -> "Fulano curtiu seu post!"
    }
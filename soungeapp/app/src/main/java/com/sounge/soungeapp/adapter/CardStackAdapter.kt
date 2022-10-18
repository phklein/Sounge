package com.sounge.soungeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sounge.soungeapp.R
import com.sounge.soungeapp.data.UserMatch
import com.squareup.picasso.Picasso
import com.yuyakaido.android.cardstackview.CardStackListener

internal class CardStackAdapter(
    private val cardList: List<UserMatch>,
    private val context: Context,
    private val fragment: CardStackListener
    ) : RecyclerView.Adapter<CardStackAdapter.CardViewHolder>() {

    internal inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var userMatchId: Long = 0

        val civProfilePic: AppCompatImageView = view.findViewById(R.id.civ_image)
        var civName: AppCompatTextView = view.findViewById(R.id.ctv_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_match, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = cardList[position]
        holder.userMatchId = item.id

        if (URLUtil.isValidUrl(item.profilePic)) {
            Picasso.get().load(item.profilePic).into(holder.civProfilePic)
        }

        holder.civName.text = item.name
    }

    override fun getItemCount(): Int {
        return cardList.size
    }
}
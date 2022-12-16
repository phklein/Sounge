package com.sounge.soungeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sounge.soungeapp.R
import com.sounge.soungeapp.response.UserContact
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ContactAdapter(
    private val items: List<UserContact>,
    private val onClick: (item: UserContact) -> Unit
    ) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {
        fun bind(item: UserContact) {
            val llContact = view.findViewById<LinearLayout>(R.id.llContact)
            val tvNameCt = view.findViewById<TextView>(R.id.tvNameCt)
            val civProfilePic = view.findViewById<CircleImageView>(R.id.civProfilePicCt)

            tvNameCt.text = item.name

            if (URLUtil.isValidUrl(item.profilePic)) {
                Picasso.get().load(item.profilePic).into(civProfilePic)
            } else {
                Picasso.get().load(R.drawable.ic_blank_profile).into(civProfilePic)
            }

            llContact.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.res_card_item, parent, false)

        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
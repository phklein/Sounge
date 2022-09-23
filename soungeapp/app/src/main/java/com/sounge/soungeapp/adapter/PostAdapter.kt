package com.sounge.soungeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.sounge.soungeapp.R

// TODO: Trocar paramentro itemList para o tipo do post
internal class PostAdapter(private var itemsList: List<String>) :
    RecyclerView.Adapter<PostAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPostOwnerName: TextView = view.findViewById(R.id.tv_post_owner_name)
        val tvPostText: TextView = view.findViewById(R.id.tv_post_text)
        val ivPostMedia: ImageView = view.findViewById(R.id.iv_post_media)

        val tvLikeAmount: TextView = view.findViewById(R.id.tv_like_amount)
        val tvCommentAmount: TextView = view.findViewById(R.id.tv_comment_amount)

        val ivLikeButton: ImageView = view.findViewById(R.id.iv_like_button)
        val ivCommentButton: ImageView = view.findViewById(R.id.iv_comment_button)
        val ivShareButton: ImageView = view.findViewById(R.id.iv_share_button)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_post, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        // TODO: atribuir valor aos campos
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}
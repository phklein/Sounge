package com.sounge.soungeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sounge.soungeapp.R
import com.sounge.soungeapp.response.CommentSimple
import com.sounge.soungeapp.listeners.CommentEventListener
import com.sounge.soungeapp.response.UserLogin
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.FormatUtils
import com.sounge.soungeapp.utils.ImageUtils
import com.squareup.picasso.Picasso

internal class CommentAdapter(private val itemsList: List<CommentSimple>,
                              private val context: Context,
                              private val commentEventListener: CommentEventListener) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    internal inner class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCommentOwnerPicture: ImageView = view.findViewById(R.id.iv_comment_owner_picture)
        val tvCommentOwnerName: TextView = view.findViewById(R.id.tv_comment_owner_name)
        val tvCommentHoursPast: TextView = view.findViewById(R.id.tv_comment_hours_past)

        val tvCommentText: TextView = view.findViewById(R.id.tv_comment_text)
        val ivCommentMedia: ImageView = view.findViewById(R.id.iv_comment_media)

        val tvCommentLikeAmount: TextView = view.findViewById(R.id.tv_comment_like_amount)

        val ivCommentLikeButton: ImageView = view.findViewById(R.id.iv_comment_like_button)
        val ivCommentShareButton: ImageView = view.findViewById(R.id.iv_comment_share_button)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_comment, parent, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val item = itemsList[position]

        holder.tvCommentOwnerName.text = item.user.name
        holder.tvCommentHoursPast.text = FormatUtils.formatHoursPast(item.hoursPast)

        if (URLUtil.isValidUrl(item.user.profilePic)) {
            Picasso.get().load(item.user.profilePic).into(holder.ivCommentOwnerPicture)
        } else {
            Picasso.get().load(R.drawable.ic_blank_profile).into(holder.ivCommentOwnerPicture)
        }

        if (item.text.isNotEmpty()) {
            holder.tvCommentText.text = item.text
        } else {
            holder.tvCommentText.visibility = View.GONE
        }

        if (URLUtil.isValidUrl(item.mediaUrl)) {
            Picasso.get().load(item.mediaUrl).into(holder.ivCommentMedia)
        } else {
            holder.ivCommentMedia.visibility = View.GONE
        }

        holder.tvCommentLikeAmount.text = FormatUtils.formatLikeAndCommentCount(item.likeCount)

        if (item.hasLiked) {
            holder.ivCommentLikeButton.setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.ic_liked)
            )
        } else {
            holder.ivCommentLikeButton.setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.ic_like)
            )
        }

        setListeners(holder, position, item)
    }

    private fun setListeners(holder: CommentViewHolder, position: Int, item: CommentSimple) {
        val userClient = Retrofit.getInstance().create(UserClient::class.java)

        holder.ivCommentMedia.setOnClickListener {
            ImageUtils.popupImage(
                holder.ivCommentMedia.drawable,
                holder.itemView
            )
        }

        holder.ivCommentLikeButton.setOnClickListener {
            if (item.hasLiked) {
                commentEventListener.onUnlike(position)
            } else {
                commentEventListener.onLike(position)
            }
        }

        holder.ivCommentShareButton.setOnClickListener {
            // TODO: Abrir janela de compartilhamento
        }

        holder.itemView.setOnLongClickListener {

            val popupMenu = PopupMenu(context, it)
            popupMenu.inflate(R.menu.comment_context_menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.mi_delete_comment -> {
                        // TODO: Excluir comentário
                    }
                }
                true
            }

            popupMenu.show()
            true
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): CommentSimple {
        return itemsList[position]
    }
}
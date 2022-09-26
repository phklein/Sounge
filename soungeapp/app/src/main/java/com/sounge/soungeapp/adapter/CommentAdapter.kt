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
import androidx.recyclerview.widget.RecyclerView
import com.sounge.soungeapp.R
import com.sounge.soungeapp.data.CommentSimple
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.FormatUtils
import com.sounge.soungeapp.utils.ImageUtils
import com.squareup.picasso.Picasso

internal class CommentAdapter(private val itemsList: List<CommentSimple>,
                              private val context: Context) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    internal inner class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var commentId: Long = 0
        var userId: Long = 0

        val ivCommentOwnerPicture: ImageView = view.findViewById(R.id.iv_comment_owner_picture)
        val tvCommentOwnerName: TextView = view.findViewById(R.id.tv_comment_owner_name)
        val tvHoursPast: TextView = view.findViewById(R.id.tv_hours_past)

        val tvCommentText: TextView = view.findViewById(R.id.tv_comment_text)
        val ivCommentMedia: ImageView = view.findViewById(R.id.iv_comment_media)

        val ivShareButton: ImageView = view.findViewById(R.id.iv_share_button)

        init {
            // TODO: Setar listener se é o dono do comentário
            view.setOnLongClickListener {

                val popupMenu = PopupMenu(view.context, it)
                popupMenu.inflate(R.menu.comment_context_menu)

                popupMenu.setOnMenuItemClickListener {item->
                    when(item.itemId) {
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
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_comment, parent, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val item = itemsList[position]
        holder.commentId = item.id
        holder.userId = item.user.id

        holder.tvCommentOwnerName.text = item.user.name
        holder.tvHoursPast.text = FormatUtils.formatHoursPast(item.hoursPast)

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

        setListeners(holder, position)
    }

    private fun setListeners(holder: CommentViewHolder, position: Int) {
        val userClient = Retrofit.getInstance().create(UserClient::class.java)

        holder.ivCommentMedia.setOnClickListener {
            ImageUtils.popupImage(holder.ivCommentMedia.drawable,
                holder.itemView)
        }

        holder.ivShareButton.setOnClickListener {
            // TODO: Abrir janela de compartilhamento
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): CommentSimple {
        return itemsList[position]
    }
}
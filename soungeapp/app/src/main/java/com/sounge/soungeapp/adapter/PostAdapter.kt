package com.sounge.soungeapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.CommentActivity
import com.sounge.soungeapp.data.PostSimple
import com.sounge.soungeapp.listeners.PostEventListener
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.FormatUtils
import com.sounge.soungeapp.utils.ImageUtils
import com.squareup.picasso.Picasso

internal class PostAdapter(private val itemsList: List<PostSimple>,
                           private val context: Context,
                           private val fragment: PostEventListener
) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    internal inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var postId: Long = 0
        var userId: Long = 0
        var hasLiked: Boolean = false

        val ivPostOwnerPicture: ImageView = view.findViewById(R.id.iv_post_owner_picture)
        val tvPostOwnerName: TextView = view.findViewById(R.id.tv_post_owner_name)
        val tvHoursPast: TextView = view.findViewById(R.id.tv_hours_past)

        val tvPostText: TextView = view.findViewById(R.id.tv_post_text)
        val ivPostMedia: ImageView = view.findViewById(R.id.iv_post_media)

        val tvLikeAmount: TextView = view.findViewById(R.id.tv_like_amount)
        val tvCommentAmount: TextView = view.findViewById(R.id.tv_comment_amount)

        val ivLikeButton: ImageView = view.findViewById(R.id.iv_like_button)
        val ivCommentButton: ImageView = view.findViewById(R.id.iv_comment_button)
        val ivShareButton: ImageView = view.findViewById(R.id.iv_share_button)

        init {
            // TODO: Setar listener se for dono do post
            view.setOnLongClickListener {

                val popupMenu = PopupMenu(view.context, it)
                popupMenu.inflate(R.menu.post_context_menu)

                popupMenu.setOnMenuItemClickListener {item->
                    when(item.itemId) {
                        R.id.mi_edit_post -> {
                            // TODO: Abrir tela de edição de post
                        }

                        R.id.mi_delete_post -> {
                            // TODO: Excluir post
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_post, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = itemsList[position]
        holder.postId = item.id
        holder.userId = item.user.id
        holder.hasLiked = item.hasLiked

        holder.tvPostOwnerName.text = item.user.name
        holder.tvHoursPast.text = FormatUtils.formatHoursPast(item.hoursPast)

        if (URLUtil.isValidUrl(item.user.profilePic)) {
            Picasso.get().load(item.user.profilePic).into(holder.ivPostOwnerPicture)
        } else {
            Picasso.get().load(R.drawable.ic_blank_profile).into(holder.ivPostOwnerPicture)
        }

        if (item.text.isNotEmpty()) {
            holder.tvPostText.text = item.text
        } else {
            holder.tvPostText.visibility = View.GONE
        }

        if (URLUtil.isValidUrl(item.mediaUrl)) {
            Picasso.get().load(item.mediaUrl).into(holder.ivPostMedia)
        } else {
            holder.ivPostMedia.visibility = View.GONE
        }

        holder.tvLikeAmount.text = FormatUtils.formatLikeAndCommentCount(item.likeCount)
        holder.tvCommentAmount.text = FormatUtils.formatLikeAndCommentCount(item.commentCount)

        if (item.hasLiked) {
            holder.ivLikeButton.setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.ic_liked)
            )
        } else {
            holder.ivLikeButton.setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.ic_like)
            )
        }

        setListeners(holder, position)
    }

    private fun setListeners(holder: PostViewHolder, position: Int) {
        val userClient = Retrofit.getInstance().create(UserClient::class.java)

        holder.ivPostMedia.setOnClickListener {
            ImageUtils.popupImage(holder.ivPostMedia.drawable,
                holder.itemView)
        }

        holder.ivLikeButton.setOnClickListener {
            if (holder.hasLiked) {
                fragment.onUnlike(position)
            } else {
                fragment.onLike(position)
            }
        }

        holder.ivCommentButton.setOnClickListener {
            val intent = Intent(context, CommentActivity::class.java)
            startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): PostSimple {
        return itemsList[position]
    }
}
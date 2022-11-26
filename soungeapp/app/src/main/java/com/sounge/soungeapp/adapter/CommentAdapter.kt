package com.sounge.soungeapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.view.*
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sounge.soungeapp.R
import com.sounge.soungeapp.listeners.CommentEventListener
import com.sounge.soungeapp.response.CommentSimple
import com.sounge.soungeapp.response.UserLogin
import com.sounge.soungeapp.rest.PostClient
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.utils.FormatUtils
import com.sounge.soungeapp.utils.ImageUtils
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class CommentAdapter(
    private val originPostId: Long,
    private val itemsList: MutableList<CommentSimple>,
    private val viewer: UserLogin,
    private val context: Context,
    private val commentEventListener: CommentEventListener
) :
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

    private fun setListeners(holder: CommentViewHolder, position: Int, comment: CommentSimple) {
        holder.ivCommentMedia.setOnClickListener {
            ImageUtils.popupImage(
                holder.ivCommentMedia.drawable,
                holder.itemView
            )
        }

        holder.ivCommentLikeButton.setOnClickListener {
            if (comment.hasLiked) {
                commentEventListener.onUnlike(position)
            } else {
                commentEventListener.onLike(position)
            }
        }

        holder.ivCommentShareButton.setOnClickListener {
            // TODO: Abrir janela de compartilhamento
        }

        if (comment.user.id == viewer.id) {
            holder.itemView.setOnLongClickListener {
                val contextWrapper = ContextThemeWrapper(context, R.style.PopupMenuStyle)
                val popupMenu = PopupMenu(contextWrapper, it)

                popupMenu.inflate(R.menu.comment_context_menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.mi_edit_comment -> {
                            // TODO: Abrir tela de edição de comentário
                        }

                        R.id.mi_delete_comment -> {
                            createConfirmationDialog(comment, position).show()
                        }
                    }
                    true
                }
                popupMenu.show()
                true
            }
        }
    }

    private fun createConfirmationDialog(comment: CommentSimple, position: Int): androidx.appcompat.app.AlertDialog {
        val builder = MaterialAlertDialogBuilder(context)
        builder.apply {
            setPositiveButton(R.string.delete) { dialog, _ ->
                val postClient = Retrofit.getInstance().create(
                    PostClient::class.java
                )
                val deletePost = postClient.deleteComment(
                    originPostId, comment.id
                )

                deletePost.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        showMessage(
                            context.getString(
                                R.string.successfully_deleted_comment
                            )
                        )
                        dialog.dismiss()
                        itemsList.remove(comment)
                        commentEventListener.onDelete(position)
                    }

                    override fun onFailure(
                        call: Call<ResponseBody>,
                        t: Throwable
                    ) {
                        showMessage(
                            context.getString(
                                R.string.error_deleting_comment
                            )
                        )
                        dialog.dismiss()
                    }

                })
            }
            setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.setTitle(context.getString(R.string.delete_comment))
        builder.setMessage(context.getString(R.string.ask_before_delete_comment))
        return builder.create()
    }

    private fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): CommentSimple {
        return itemsList[position]
    }
}
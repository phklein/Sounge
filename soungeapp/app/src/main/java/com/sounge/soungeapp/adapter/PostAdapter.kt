package com.sounge.soungeapp.adapter

import android.app.AlertDialog
import android.content.Context
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
import com.sounge.soungeapp.listeners.PostEventListener
import com.sounge.soungeapp.response.PostSimple
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

internal class PostAdapter(
    private val itemsList: MutableList<PostSimple>,
    private val viewer: UserLogin,
    private val context: Context,
    private val postEventListener: PostEventListener
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    internal inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_post, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = itemsList[position]

        holder.tvPostOwnerName.text = if (item.user != null)
            item.user?.name else item.group?.name
        holder.tvHoursPast.text = FormatUtils.formatHoursPast(item.hoursPast)

        if (URLUtil.isValidUrl(item.user?.profilePic)) {
            Picasso.get().load(item.user?.profilePic).into(holder.ivPostOwnerPicture)
        } else if (URLUtil.isValidUrl(item.group?.profilePic)) {
            Picasso.get().load(item.group?.profilePic).into(holder.ivPostOwnerPicture)
        } else {
            Picasso.get().load(R.drawable.ic_blank_profile).into(holder.ivPostOwnerPicture)
        }

        if (item.text?.isNotEmpty() == true) {
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

        setListeners(holder, position, item)
    }

    private fun setListeners(holder: PostViewHolder, position: Int, post: PostSimple) {
        holder.ivPostMedia.setOnClickListener {
            ImageUtils.popupImage(
                holder.ivPostMedia.drawable,
                holder.itemView
            )
        }

        holder.ivLikeButton.setOnClickListener {
            if (post.hasLiked) {
                postEventListener.onUnlike(position)
            } else {
                postEventListener.onLike(position)
            }
        }

        holder.ivCommentButton.setOnClickListener {
            postEventListener.onClickComment(post, position)
        }

        // TODO: Também mostrar caso usuário seja parte da banda
        if (post.user?.id == viewer.id) {
            holder.itemView.setOnLongClickListener {
                val contextWrapper = ContextThemeWrapper(context, R.style.PopupMenuStyle)
                val popupMenu = PopupMenu(contextWrapper, it)

                popupMenu.inflate(R.menu.post_context_menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.mi_edit_post -> {
                            // TODO: Abrir tela de edição de post
                        }

                        R.id.mi_delete_post -> {
                            createConfirmationDialog(post, position).show()
                        }
                    }
                    true
                }

                popupMenu.show()
                true
            }
        }
    }

    private fun createConfirmationDialog(post: PostSimple, position: Int): androidx.appcompat.app.AlertDialog {
        val builder = MaterialAlertDialogBuilder(context)
        builder.apply {
            setPositiveButton(R.string.delete) { dialog, _ ->
                val postClient = Retrofit.getInstance().create(
                    PostClient::class.java
                )
                val deletePost = postClient.deletePost(post.id)

                deletePost.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        showMessage(
                            context.getString(
                                R.string.successfully_deleted_post
                            )
                        )
                        dialog.dismiss()
                        itemsList.remove(post)
                        postEventListener.onDelete(position)
                    }

                    override fun onFailure(
                        call: Call<ResponseBody>,
                        t: Throwable
                    ) {
                        showMessage(
                            context.getString(
                                R.string.error_deleting_post
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
        builder.setTitle(context.getString(R.string.delete_post))
        builder.setMessage(context.getString(R.string.ask_before_delete_post))
        return builder.create()
    }

    private fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): PostSimple {
        return itemsList[position]
    }
}
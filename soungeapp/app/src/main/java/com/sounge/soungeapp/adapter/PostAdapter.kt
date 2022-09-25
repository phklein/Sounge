package com.sounge.soungeapp.adapter

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sounge.soungeapp.R
import com.sounge.soungeapp.data.PostSimple
import com.sounge.soungeapp.fragment.PostRelated
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.ImageUtils
import com.squareup.picasso.Picasso

internal class PostAdapter(private val itemsList: List<PostSimple>,
                           private val context: Context,
                           private val fragment: PostRelated) :
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
        holder.tvHoursPast.text = formatHoursPast(item.hoursPast)

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

        holder.tvLikeAmount.text = item.likeCount.toString()
        holder.tvCommentAmount.text = item.commentCount.toString()

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

    private fun formatHoursPast(hoursPast: Long) : String {
        if (hoursPast > 8760) {
            val years = Math.floorDiv(hoursPast, 8760)

            return "%d%s".format(years, "a")
        }

        if (hoursPast > 730) {
            val months = Math.floorDiv(hoursPast, 730)

            return "%d%s".format(months, "me")
        }

        if (hoursPast > 168) {
            val weeks = Math.floorDiv(hoursPast, 168)

            return "%d%s".format(weeks, "s")
        }

        if (hoursPast > 24) {
            val days = Math.floorDiv(hoursPast, 24)

            return "%d%s".format(days, "d")
        }

        return "%d%s".format(hoursPast, "h")
    }

    private fun setListeners(holder: PostViewHolder, position: Int) {
        val userClient = Retrofit.getInstance().create(UserClient::class.java)

        holder.ivPostMedia.setOnClickListener {
            ImageUtils.popupImage(holder.ivPostMedia.drawable, holder.itemView)
        }

        holder.ivLikeButton.setOnClickListener {
            if (holder.hasLiked) {
//                val callback = userClient.unlikePost(holder.userId, holder.postId)
//
//                callback.enqueue(object : Callback<ResponseBody> {
//                    override fun onResponse(
//                        call: Call<ResponseBody>,
//                        response: Response<ResponseBody>
//                    ) {
                fragment.onUnlike(position)
//                    }

//                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                        Toast.makeText(
//                            activity.baseContext,
//                            activity.baseContext.getString(R.string.error_unliking),
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//
//                })
            } else {
//                val callback = userClient.likePost(holder.userId, holder.postId)
//
//                callback.enqueue(object : Callback<ResponseBody> {
//                    override fun onResponse(
//                        call: Call<ResponseBody>,
//                        response: Response<ResponseBody>
//                    ) {
                fragment.onLike(position)
//                    }
//
//                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                        Toast.makeText(
//                            activity.baseContext,
//                            activity.baseContext.getString(R.string.error_liking),
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                })
            }
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): PostSimple {
        return itemsList[position]
    }
}
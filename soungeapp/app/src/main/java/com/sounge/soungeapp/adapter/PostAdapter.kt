package com.sounge.soungeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.sounge.soungeapp.R
import com.sounge.soungeapp.data.PostSimple
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Trocar paramentro itemList para o tipo do post
internal class PostAdapter(private val itemsList: List<PostSimple>, private val context: Context) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    internal inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var postId: Long = 0
        var userId: Long = 0
        var hasLiked: Boolean = false

        val tvPostOwnerName: TextView = view.findViewById(R.id.tv_post_owner_name)
        val ivPostOwnerPicture: ImageView = view.findViewById(R.id.iv_post_owner_picture)

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
        Picasso.get().load(item.user.profilePic).into(holder.ivPostOwnerPicture)

        holder.tvPostText.text = item.text
        Picasso.get().load(item.user.profilePic).into(holder.ivPostMedia)

        holder.tvLikeAmount.text = item.likeCount.toString()
        holder.tvCommentAmount.text = item.commentCount.toString()

        if (item.hasLiked) {
            Picasso.get().load(R.drawable.ic_liked).into(holder.ivLikeButton)
        }

        setListeners(holder, position)
    }

    private fun setListeners(holder: PostViewHolder, position: Int) {
        val userClient = Retrofit.getInstance().create(UserClient::class.java)

        holder.ivLikeButton.setOnClickListener {
            if (holder.hasLiked) {
                val callback = userClient.unlikePost(holder.userId, holder.postId)

                callback.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        subOne(holder.tvLikeAmount)
                        Picasso.get().load(R.drawable.ic_like).into(holder.ivLikeButton)

                        holder.hasLiked = false
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.error_unliking),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
            } else {
                val callback = userClient.likePost(holder.userId, holder.postId)

                callback.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        addOne(holder.tvLikeAmount)
                        Picasso.get().load(R.drawable.ic_liked).into(holder.ivLikeButton)

                        holder.hasLiked = true
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.error_liking),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }

            notifyItemChanged(position)
        }
    }

    private fun addOne(textView: TextView) {
        val current = textView.text.toString().toInt()
        textView.text = (current + 1).toString()
    }

    private fun subOne(textView: TextView) {
        val current = textView.text.toString().toInt()
        textView.text = (current - 1).toString()
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}
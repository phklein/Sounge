package com.sounge.soungeapp.actitivy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.WritingActivity.Constants.USER_NEW_COMMENT_KEY
import com.sounge.soungeapp.adapter.CommentAdapter
import com.sounge.soungeapp.databinding.ActivityCommentBinding
import com.sounge.soungeapp.fragment.ProfileFragment
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.NEW_COMMENT_AMOUNT_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.ORIGIN_POST_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.ORIGIN_POST_POSITION_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.VIEWER_KEY
import com.sounge.soungeapp.listeners.CommentEventListener
import com.sounge.soungeapp.response.CommentSimple
import com.sounge.soungeapp.response.Page
import com.sounge.soungeapp.response.PostSimple
import com.sounge.soungeapp.response.UserSimple
import com.sounge.soungeapp.rest.PostClient
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import okhttp3.ResponseBody
import org.w3c.dom.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentActivity : AppCompatActivity(), CommentEventListener {
    private lateinit var binding: ActivityCommentBinding

    private var originPostPosition: Int = -1
    private lateinit var originPost: PostSimple
    private lateinit var viewer: UserSimple

    private lateinit var commentPage: Page<CommentSimple>

    private lateinit var adapter: CommentAdapter

    private lateinit var userClient: UserClient
    private lateinit var postClient: PostClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        originPost = GsonUtils.INSTANCE.fromJson(
            intent.getStringExtra(ORIGIN_POST_KEY),
            PostSimple::class.java
        )

        originPostPosition = intent.getIntExtra(ORIGIN_POST_POSITION_KEY, -1)

        viewer = GsonUtils.INSTANCE.fromJson(
            intent.getStringExtra(VIEWER_KEY),
            UserSimple::class.java
        )

        userClient = Retrofit.getInstance().create(UserClient::class.java)
        postClient = Retrofit.getInstance().create(PostClient::class.java)

        getComments()
    }

    private fun getComments() {
        val comments = postClient.getComments(originPost.id, viewer.id, 0)

        comments.enqueue(object : Callback<Page<CommentSimple>> {
            override fun onResponse(
                call: Call<Page<CommentSimple>>,
                response: Response<Page<CommentSimple>>
            ) {
                if (response.code() in 200..299) {
                    commentPage = response.body()!!

                    setupRecyclerView(commentPage.isEmpty)
                    setupActionBar()
                    setListeners()
                } else {
                    showError(getString(R.string.get_comments_error))
                    onBackPressed()
                }
            }

            override fun onFailure(call: Call<Page<CommentSimple>>, t: Throwable) {
                showError(getString(R.string.get_comments_error))
                onBackPressed()
            }
        })
    }

    private fun showError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    @SuppressLint("SetTextI18n")
    private fun setupActionBar() {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_back)

        findViewById<ImageView>(R.id.iv_back_button).setOnClickListener {
            onBackPressed()
        }

        findViewById<TextView>(R.id.tv_page_name).text =
            "Post de %s".format(if (originPost.user != null)
                originPost.user?.name else originPost.group?.name
            )
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(
            ORIGIN_POST_POSITION_KEY,
            originPostPosition
        )
        intent.putExtra(
            NEW_COMMENT_AMOUNT_KEY,
            originPost.commentCount
        )
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun setupRecyclerView(empty: Boolean) {
        if (empty) {
            binding.tvNoComments.visibility = View.VISIBLE
            binding.rvPostComments.visibility = View.GONE
        } else {
            val layoutManager = LinearLayoutManager(this)

            adapter = CommentAdapter(commentPage.content, this, this)

            binding.rvPostComments.layoutManager = layoutManager
            binding.rvPostComments.adapter = adapter

            registerForContextMenu(binding.rvPostComments)
        }
    }

    private fun setListeners() {
        binding.fabWriteComment.setOnClickListener {
            val intent = Intent(this, WritingActivity::class.java)
            intent.putExtra(
                ORIGIN_POST_KEY,
                originPost.id
            )
            intent.putExtra(
                VIEWER_KEY,
                GsonUtils.INSTANCE.toJson(viewer)
            )
            startActivityForResult(intent, ProfileFragment.Constants.POST_WRITING_REQUEST_CODE)
        }

        binding.rvPostComments.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(v: RecyclerView, newState: Int) {
                super.onScrollStateChanged(v, newState)

                if (!v.canScrollVertically(1) && !commentPage.last) {
                    val comments = postClient.getComments(
                        originPost.id, viewer.id, commentPage.number++)

                    comments.enqueue(object : Callback<Page<CommentSimple>> {
                        override fun onResponse(
                            call: Call<Page<CommentSimple>>,
                            response: Response<Page<CommentSimple>>
                        ) {
                            if (response.code() == 204) {
                                //
                            } else if (response.code() in 200..299) {
                                val content = response.body()!!.content
                                val start = content.size - 1

                                content.forEach {
                                    commentPage.content.add(it)
                                }

                                adapter.notifyItemRangeInserted(start, content.size)
                            } else {
                                showError(getString(R.string.post_error))
                            }
                        }

                        override fun onFailure(call: Call<Page<CommentSimple>>, t: Throwable) {
                            showError(getString(R.string.post_error))
                        }

                    })
                }

                if (!v.canScrollVertically(0)) {
                    getComments()
                }
            }
        })
    }

    override fun onLike(position: Int) {
        val comment = adapter.getItem(position)
        val likeComment = userClient.likeComment(viewer.id, comment.id)

        likeComment.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() in 200..299) {
                    comment.likeCount++
                    comment.hasLiked = true
                    adapter.notifyItemChanged(position, comment)
                } else {
                    showError(getString(R.string.like_comment_error))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                showError(getString(R.string.like_comment_error))
            }
        })
    }

    override fun onUnlike(position: Int) {
        val comment = adapter.getItem(position)
        val unlikeComment = userClient.unlikeComment(viewer.id, comment.id)

        unlikeComment.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() in 200..299) {
                    comment.likeCount--
                    comment.hasLiked = false
                    adapter.notifyItemChanged(position, comment)
                } else {
                    showError(getString(R.string.unlike_error))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                showError(getString(R.string.unlike_error))
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ProfileFragment.Constants.POST_WRITING_REQUEST_CODE &&
            resultCode == RESULT_OK
        ) {
            val newComment = GsonUtils.INSTANCE.fromJson(
                data?.getStringExtra(USER_NEW_COMMENT_KEY),
                CommentSimple::class.java
            )

            commentPage.content.add(0, newComment)
            adapter.notifyItemInserted(0);
            binding.rvPostComments.smoothScrollToPosition(0)

            originPost.commentCount++
        }
    }
}
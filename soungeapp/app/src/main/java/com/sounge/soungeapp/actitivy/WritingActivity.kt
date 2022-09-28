package com.sounge.soungeapp.actitivy

import android.content.Intent
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.WritingActivity.Constants.USER_NEW_COMMENT_KEY
import com.sounge.soungeapp.actitivy.WritingActivity.Constants.USER_NEW_POST_KEY
import com.sounge.soungeapp.actitivy.WritingActivity.Hints.hintList
import com.sounge.soungeapp.databinding.ActivityWritingBinding
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.ORIGIN_POST_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.VIEWER_KEY
import com.sounge.soungeapp.request.CreateComment
import com.sounge.soungeapp.request.CreatePost
import com.sounge.soungeapp.response.CommentSimple
import com.sounge.soungeapp.response.PostSimple
import com.sounge.soungeapp.response.UserLogin
import com.sounge.soungeapp.response.UserSimple
import com.sounge.soungeapp.rest.PostClient
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.utils.GsonUtils
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class WritingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWritingBinding

    private lateinit var originUser: UserLogin

    private lateinit var postClient: PostClient

    object Constants {
        const val USER_NEW_POST_KEY = "userNewPost"
        const val USER_NEW_COMMENT_KEY = "userNewComment"
    }

    object Hints {
        val hintList: IntArray = intArrayOf(
            R.string.post_hint0,
            R.string.post_hint1,
            R.string.post_hint2,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val originPostId = intent.getLongExtra(ORIGIN_POST_KEY, -1)

        originUser = GsonUtils.INSTANCE.fromJson(
            intent.getStringExtra(VIEWER_KEY),
            UserLogin::class.java
        )

        postClient = Retrofit.getInstance().create(PostClient::class.java)

        setRandomHint()
        setUserPhoto()
        setupActionBar(originPostId)
    }

    private fun setRandomHint() {
        binding.etCreatePostText.hint = getString(hintList[Random.nextInt(0, 2)])
    }

    private fun setUserPhoto() {
        if (URLUtil.isValidUrl(originUser.profilePic)) {
            Picasso.get().load(originUser.profilePic).into(binding.ivWritingUserPicture)
        } else {
            Picasso.get().load(R.drawable.ic_blank_profile).into(binding.ivWritingUserPicture)
        }
    }

    private fun setupActionBar(originPostId: Long) {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_edit)

        findViewById<TextView>(R.id.tv_action_cancel).setOnClickListener {
            onBackPressed()
        }

        findViewById<TextView>(R.id.tv_action_save).setOnClickListener {
            val text = binding.etCreatePostText.text.toString()

            if (originPostId < 1) {
                val createPost = postClient.createPost(
                    CreatePost(
                        originUser.id,
                        null,
                        text,
                        ""
                    )
                )

                createPost.enqueue(object : Callback<Long> {
                    override fun onResponse(call: Call<Long>, response: Response<Long>) {
                        if (response.code() in 200..299) {
                            val intent = Intent()

                            intent.putExtra(
                                USER_NEW_POST_KEY,
                                GsonUtils.INSTANCE.toJson(
                                    PostSimple(
                                        response.body()!!,
                                        text,
                                        "",
                                        0,
                                        UserSimple(
                                            originUser.id,
                                            originUser.name,
                                            originUser.profilePic,
                                            originUser.leader
                                        ),
                                        null,
                                        0,
                                        0,
                                        false
                                    )
                                )
                            )

                            setResult(RESULT_OK, intent)
                            finish()
                        } else {
                            showError(getString(R.string.posting_eror))
                        }
                    }

                    override fun onFailure(call: Call<Long>, t: Throwable) {
                        showError(getString(R.string.posting_eror))
                    }
                })
            } else {
                val createComment = postClient.createComment(
                    originPostId,
                    CreateComment(
                        originUser.id,
                        text,
                        ""
                    )
                )

                createComment.enqueue(object : Callback<Long> {
                    override fun onResponse(call: Call<Long>, response: Response<Long>) {
                        if (response.code() in 200..299) {
                            val intent = Intent()

                            intent.putExtra(
                                USER_NEW_COMMENT_KEY,
                                GsonUtils.INSTANCE.toJson(
                                    CommentSimple(
                                        response.body()!!,
                                        text,
                                        "",
                                        0,
                                        UserSimple(
                                            originUser.id,
                                            originUser.name,
                                            originUser.profilePic,
                                            originUser.leader
                                        ),
                                        0,
                                        false
                                    )
                                )
                            )

                            setResult(RESULT_OK, intent)
                            finish()
                        } else {
                            showError(getString(R.string.commenting_error))
                        }
                    }

                    override fun onFailure(call: Call<Long>, t: Throwable) {
                        showError(getString(R.string.commenting_error))
                    }
                })
            }
        }
    }

    private fun showError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
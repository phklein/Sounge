package com.sounge.soungeapp.actitivy

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.EditPostActivity.Constants.NEW_POST_KEY
import com.sounge.soungeapp.databinding.ActivityEditPostBinding
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.ORIGIN_POST_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.ORIGIN_POST_POSITION_KEY
import com.sounge.soungeapp.request.UpdatePost
import com.sounge.soungeapp.response.CommentSimple
import com.sounge.soungeapp.response.PostSimple
import com.sounge.soungeapp.rest.PostClient
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.utils.GsonUtils
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditPostBinding

    private var originPosition: Int = -1
    private lateinit var originPost: PostSimple
    private lateinit var originComment: CommentSimple

    private lateinit var postClient: PostClient

    object Constants {
        const val NEW_POST_KEY = "newPost"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        originPosition = intent.getIntExtra(ORIGIN_POST_POSITION_KEY, -1)


        originPost = GsonUtils.INSTANCE.fromJson(
            intent.getStringExtra(ORIGIN_POST_KEY),
            PostSimple::class.java
        )

        val newMediaUrl = originPost.mediaUrl

        postClient = Retrofit.getInstance().create(PostClient::class.java)

        setupActionBar(newMediaUrl)
        setCurrentInfo()
        setListeners()
    }

    private fun setupActionBar(newMediaUrl: String?) {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_edit)

        findViewById<TextView>(R.id.tv_action_cancel).setOnClickListener {
            onBackPressed()
        }

        findViewById<TextView>(R.id.tv_action_save).setOnClickListener {
            val newText = binding.etPostText.text.toString()

            if (newText != originPost.text || newMediaUrl != originPost.mediaUrl) {
                updatePost(newText, newMediaUrl)
            } else {
                onBackPressed()
            }
        }
    }

    private fun updatePost(newText: String, newMediaUrl: String?) {
        val updatePost = postClient.updatePost(
            originPost.id, UpdatePost(newText, originPost.mediaUrl ?: "")
        )

        updatePost.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() in 200..299) {
                    originPost.text = newText
                    originPost.mediaUrl = newMediaUrl

                    val intent = Intent()

                    intent.putExtra(
                        NEW_POST_KEY,
                        GsonUtils.INSTANCE.toJson(originPost)
                    )

                    setResult(RESULT_OK, intent)
                    finish()
                    return
                } else {
                    showSavingError()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                showSavingError()
            }
        })
    }

    private fun showSavingError() {
        Toast.makeText(applicationContext, R.string.saving_error, Toast.LENGTH_LONG).show()
    }

    private fun setCurrentInfo() {
        Picasso.get().load(originPost.mediaUrl).into(binding.ivEditMediaUrl)

        binding.etPostText.setText(originPost.text)
    }

    private fun setListeners() {
        binding.ivEditMediaUrl.setOnClickListener {
            // TODO: Abrir popup pra subir imagem
        }
    }
}
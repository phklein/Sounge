package com.sounge.soungeapp.actitivy

import android.content.Intent
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.WritingActivity.Constants.USER_NEW_COMMENT_KEY
import com.sounge.soungeapp.actitivy.WritingActivity.Constants.USER_NEW_POST_KEY
import com.sounge.soungeapp.actitivy.WritingActivity.Hints.hintList
import com.sounge.soungeapp.data.CommentSimple
import com.sounge.soungeapp.data.PostSimple
import com.sounge.soungeapp.data.UserSimple
import com.sounge.soungeapp.databinding.ActivityWritingBinding
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.ORIGIN_POST_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.USER_SIMPLE_KEY
import com.sounge.soungeapp.utils.GsonUtils
import com.squareup.picasso.Picasso
import kotlin.random.Random

class WritingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWritingBinding

    private lateinit var originUser: UserSimple

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

        val originPostId = intent.getLongExtra(ORIGIN_POST_KEY, 0)

        originUser = GsonUtils.INSTANCE.fromJson(
            intent.getStringExtra(USER_SIMPLE_KEY),
            UserSimple::class.java
        )

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
            // TODO: Enviar ok se conseguir salvar no banco
            val text = binding.etCreatePostText.text.toString()

            val intent = Intent()

            if (originPostId > 0) {
                intent.putExtra(
                    USER_NEW_COMMENT_KEY,
                    GsonUtils.INSTANCE.toJson(CommentSimple(
                        1,
                        text,
                        "",
                        0,
                        originUser,
                        0,
                        false
                    ))
                )
            } else {
                intent.putExtra(
                    USER_NEW_POST_KEY,
                    GsonUtils.INSTANCE.toJson(PostSimple(
                        1,
                        text,
                        "",
                        0,
                        originUser,
                        null,
                        0,
                        0,
                        false
                    ))
                )
            }

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
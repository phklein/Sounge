package com.sounge.soungeapp.actitivy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.WritingActivity.Constants.USER_NEW_COMMENT_KEY
import com.sounge.soungeapp.adapter.CommentAdapter
import com.sounge.soungeapp.response.CommentSimple
import com.sounge.soungeapp.response.PostSimple
import com.sounge.soungeapp.response.UserSimple
import com.sounge.soungeapp.databinding.ActivityCommentBinding
import com.sounge.soungeapp.fragment.ProfileFragment
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.NEW_COMMENT_AMOUNT_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.ORIGIN_POST_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.ORIGIN_POST_POSITION_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.VIEWER_KEY
import com.sounge.soungeapp.listeners.CommentEventListener
import com.sounge.soungeapp.utils.GsonUtils

class CommentActivity : AppCompatActivity(), CommentEventListener {
    private lateinit var binding: ActivityCommentBinding

    private var originPostPosition: Int = -1
    private lateinit var originPost: PostSimple
    private lateinit var viewer: UserSimple
    private lateinit var commentList: MutableList<CommentSimple>

    private lateinit var adapter: CommentAdapter

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

        commentList = mockComments()
        setupRecyclerView()

        setupActionBar()
        setListeners()
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

    private fun mockComments(): MutableList<CommentSimple> {
        val commentList = ArrayList<CommentSimple>()

        commentList.add(
            CommentSimple(
                1,
                "Olha, eu odeio rock mas seu show até q foi bom",
                "",
                20,
                UserSimple(
                    1,
                    "Fulaninho do Pagode",
                    "",
                    true
                ),
                10,
                true
            )
        )

        commentList.add(
            CommentSimple(
                1,
                "Mano, com todo respeito, mas vc parece o shrek kk",
                "https://www.ofuxico.com.br/wp-content/uploads/2021/08/shrek-sessao-da-tarde-1.jpg",
                20,
                UserSimple(
                    1,
                    "Luiza do Funk",
                    "https://s2.glbimg.com/ms-q0_2dw7-DhcfJheDbep5eWVI=/0x0:1365x2048/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_ba3db981e6d14e54bb84be31c923b00c/internal_photos/bs/2021/S/A/qLhXuMT8mnhOgfAvnzBg/2021-12-15-autoral-funk0365.jpg",
                    true
                ),
                10_000_000,
                true
            )
        )

        return commentList
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)

        adapter = CommentAdapter(commentList, this, this)

        binding.rvPostComments.layoutManager = layoutManager
        binding.rvPostComments.adapter = adapter

        registerForContextMenu(binding.rvPostComments)
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
    }

    override fun onLike(position: Int) {
        val comment = adapter.getItem(position)
        comment.likeCount++
        comment.hasLiked = true
        adapter.notifyItemChanged(position, comment)
    }

    override fun onUnlike(position: Int) {
        val comment = adapter.getItem(position)
        comment.likeCount--
        comment.hasLiked = false
        adapter.notifyItemChanged(position, comment)
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

            commentList.add(0, newComment)
            adapter.notifyItemInserted(0);
            binding.rvPostComments.smoothScrollToPosition(0)

            originPost.commentCount++
        }
    }
}
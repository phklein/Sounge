package com.sounge.soungeapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.CommentActivity
import com.sounge.soungeapp.actitivy.MainActivity
import com.sounge.soungeapp.actitivy.WritingActivity
import com.sounge.soungeapp.adapter.PostAdapter
import com.sounge.soungeapp.databinding.FragmentFeedBinding
import com.sounge.soungeapp.enums.GenreName
import com.sounge.soungeapp.listeners.PostEventListener
import com.sounge.soungeapp.response.Page
import com.sounge.soungeapp.response.PostSimple
import com.sounge.soungeapp.response.UserLogin
import com.sounge.soungeapp.rest.PostClient
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class FeedFragment : Fragment(), PostEventListener {
    private lateinit var binding: FragmentFeedBinding

    private lateinit var viewer: UserLogin

    private lateinit var postList: MutableList<PostSimple>

    private lateinit var adapter: PostAdapter

    private lateinit var postClient: PostClient
    private lateinit var userClient: UserClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        viewer = GsonUtils.INSTANCE.fromJson(
            SharedPreferencesUtils.get(
                requireActivity(),
                SharedPreferencesUtils.Constants.USER_INFO_PREFS,
                SharedPreferencesUtils.Constants.USER_LOGIN_KEY
            ),
            UserLogin::class.java
        )

        postClient = Retrofit.getInstance().create(PostClient::class.java)
        userClient = Retrofit.getInstance().create(UserClient::class.java)
        getPosts(viewer.id, null, null, null, null, 0)

        return binding.root
    }

    private fun getPosts(
        userId: Long,
        genre: GenreName?,
        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?,
        textLike: String?,
        page: Int
    ) {
        val getPosts =
            postClient.getPosts(userId, genre, startDateTime, endDateTime, textLike, page)
        getPosts.enqueue(object : Callback<Page<PostSimple>> {
            override fun onResponse(
                call: Call<Page<PostSimple>>,
                response: Response<Page<PostSimple>>
            ) {
                val message: String

                if (response.code() in 200..299) {
                    postList = response.body()!!.content
                    setupRecyclerView(response.code() == 204)

                    setupActionBar()
                    setListeners()
                    return
                } else if (response.code() == 404) {
                    message = getString(R.string.profile_not_found)
                } else {
                    message = getString(R.string.profile_error)
                }

                showError(message)
                requireActivity().onBackPressed()
            }

            override fun onFailure(call: Call<Page<PostSimple>>, t: Throwable) {
                showError(getString(R.string.profile_error))
                requireActivity().onBackPressed()
            }
        })
    }

    private fun setupRecyclerView(empty: Boolean) {
        if (empty) {
            binding.tvNoPosts.visibility = View.VISIBLE
            binding.rvProfilePosts.visibility = View.GONE
        } else {
            val layoutManager = LinearLayoutManager(requireActivity())

            adapter = PostAdapter(
                postList,
                viewer,
                requireActivity(),
                this
            )

            binding.rvProfilePosts.layoutManager = layoutManager
            binding.rvProfilePosts.adapter = adapter

            registerForContextMenu(binding.rvProfilePosts);
        }
    }

    private fun setupActionBar() {
        val activity = requireActivity() as AppCompatActivity

        activity.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        activity.supportActionBar!!.setDisplayShowCustomEnabled(true)
        activity.supportActionBar!!.setCustomView(R.layout.action_bar_back)

        activity.findViewById<ImageView>(R.id.iv_back_button).setOnClickListener {
            activity.onBackPressed()
        }
    }

    private fun setListeners() {
        binding.fabWritePost.setOnClickListener {
            val intent = Intent(requireActivity(), WritingActivity::class.java)
            intent.putExtra(
                ProfileFragment.Constants.VIEWER_KEY,
                GsonUtils.INSTANCE.toJson(viewer)
            )
            startActivityForResult(intent, ProfileFragment.Constants.POST_WRITING_REQUEST_CODE)
        }

//        binding.ivSearchButton.setOnClickListener {
//            val args = Bundle()
//            val fragment = SearchResultFragment()
//            fragment.arguments = args
//        }
    }

    override fun onLike(position: Int) {
        val post = adapter.getItem(position)
        val likePost = userClient.likePost(viewer.id, post.id)

        likePost.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() in 200..299) {
                    post.likeCount++
                    post.hasLiked = true
                    adapter.notifyItemChanged(position, post)
                } else {
                    showError(getString(R.string.like_post_error))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                showError(getString(R.string.like_post_error))
            }
        })
    }

    override fun onDelete(position: Int) {
        adapter.notifyItemRemoved(position)

        if (postList.isEmpty()) {
            setupRecyclerView(true)
        }
    }

    override fun onUnlike(position: Int) {
        val post = adapter.getItem(position)
        val unlikePost = userClient.unlikePost(viewer.id, post.id)

        unlikePost.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() in 200..299) {
                    post.likeCount--
                    post.hasLiked = false
                    adapter.notifyItemChanged(position, post)
                } else {
                    showError(getString(R.string.unlike_error))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                showError(getString(R.string.unlike_error))
            }
        })
    }

    private fun showError(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()
    }

    override fun onClickComment(post: PostSimple, position: Int) {
        val intent = Intent(context, CommentActivity::class.java)
        intent.putExtra(
            ProfileFragment.Constants.ORIGIN_POST_KEY,
            GsonUtils.INSTANCE.toJson(post)
        )
        intent.putExtra(
            ProfileFragment.Constants.ORIGIN_POST_POSITION_KEY,
            position
        )
        intent.putExtra(
            ProfileFragment.Constants.VIEWER_KEY,
            GsonUtils.INSTANCE.toJson(viewer)
        )
        startActivityForResult(intent, ProfileFragment.Constants.COMMENT_CREATION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ProfileFragment.Constants.POST_WRITING_REQUEST_CODE &&
            resultCode == AppCompatActivity.RESULT_OK
        ) {
            val newPost = GsonUtils.INSTANCE.fromJson(
                data!!.getStringExtra(WritingActivity.Constants.USER_NEW_POST_KEY),
                PostSimple::class.java
            )

            postList.add(0, newPost)
            adapter.notifyItemInserted(0)
            binding.rvProfilePosts.smoothScrollToPosition(0)
        } else if (requestCode == ProfileFragment.Constants.COMMENT_CREATION_REQUEST_CODE &&
            resultCode == AppCompatActivity.RESULT_OK
        ) {
            val position = data!!.getIntExtra(ProfileFragment.Constants.ORIGIN_POST_POSITION_KEY, 0)
            val newCommentAmount =
                data.getIntExtra(ProfileFragment.Constants.NEW_COMMENT_AMOUNT_KEY, 0)

            postList[position].commentCount = newCommentAmount
            adapter.notifyItemChanged(position)
        }
    }
}
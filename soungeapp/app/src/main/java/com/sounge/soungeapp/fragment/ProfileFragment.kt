package com.sounge.soungeapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.CommentActivity
import com.sounge.soungeapp.actitivy.EditProfileActivity
import com.sounge.soungeapp.actitivy.EditProfileActivity.Constants.USER_NEW_PROFILE_KEY
import com.sounge.soungeapp.actitivy.MainActivity.Constants.PROFILE_OWNER_ID_KEY
import com.sounge.soungeapp.actitivy.WritingActivity
import com.sounge.soungeapp.actitivy.WritingActivity.Constants.USER_NEW_POST_KEY
import com.sounge.soungeapp.adapter.PostAdapter
import com.sounge.soungeapp.databinding.FragmentProfileBinding
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.COMMENT_CREATION_REQUEST_CODE
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.NEW_COMMENT_AMOUNT_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.ORIGIN_POST_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.ORIGIN_POST_POSITION_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.POST_WRITING_REQUEST_CODE
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.PROFILE_EDIT_REQUEST_CODE
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.USER_PAGE_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.VIEWER_KEY
import com.sounge.soungeapp.listeners.PostEventListener
import com.sounge.soungeapp.response.PostSimple
import com.sounge.soungeapp.response.UserLogin
import com.sounge.soungeapp.response.UserPage
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.sounge.soungeapp.utils.ImageUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils.Constants.USER_INFO_PREFS
import com.sounge.soungeapp.utils.SharedPreferencesUtils.Constants.USER_LOGIN_KEY
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(), PostEventListener {
    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewer: UserLogin
    private lateinit var userPage: UserPage

    private lateinit var adapter: PostAdapter

    private lateinit var userClient: UserClient

    object Constants {
        const val PROFILE_EDIT_REQUEST_CODE = 1
        const val POST_WRITING_REQUEST_CODE = 2
        const val COMMENT_CREATION_REQUEST_CODE = 3

        const val ORIGIN_POST_KEY = "originPost"
        const val ORIGIN_POST_POSITION_KEY = "originPostPosition"
        const val USER_PAGE_KEY = "userPage"
        const val VIEWER_KEY = "userSimple"
        const val NEW_COMMENT_AMOUNT_KEY = "newCommentAmount"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewer = GsonUtils.INSTANCE.fromJson(
            SharedPreferencesUtils.get(requireActivity(), USER_INFO_PREFS, USER_LOGIN_KEY),
            UserLogin::class.java
        )

        val ownerId = requireArguments().getLong(PROFILE_OWNER_ID_KEY)
        userClient = Retrofit.getInstance().create(UserClient::class.java)

        getUserPage(ownerId)

        return binding.root
    }

    private fun getUserPage(ownerId: Long) {
        val getUserPage = userClient.getUserPage(ownerId, viewer.id)
        getUserPage.enqueue(object : Callback<UserPage> {
            override fun onResponse(call: Call<UserPage>, response: Response<UserPage>) {
                val message: String

                if (response.code() in 200..299) {
                    userPage = response.body()!!
                    showProfileInfo()
                    showTalentList()
                    showBandInfo()
                    setupRecyclerView()

                    hideViewsIfViewerNotOwner()

                    setupActionBar()
                    setListeners()
                    return
                } else if (response.code() == 404) {
                    message = getString(R.string.profile_not_found)
                } else {
                    message = getString(R.string.profile_error)
                }

                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                requireActivity().onBackPressed()
            }

            override fun onFailure(call: Call<UserPage>, t: Throwable) {
                Toast.makeText(context, getString(R.string.profile_error),
                    Toast.LENGTH_LONG).show()
                requireActivity().onBackPressed()
            }
        })
    }

    private fun hideViewsIfViewerNotOwner() {
        if (!userPage.viewerProfile) {
            binding.btEditProfile.visibility = View.GONE
            binding.fabWritePost.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        if (userPage.postList.isEmpty()) {
            binding.tvNoPosts.visibility = View.VISIBLE
            binding.rvProfilePosts.visibility = View.GONE
        } else {
            val layoutManager = LinearLayoutManager(requireActivity())

            // TODO: Receber viewer da main activity
            adapter = PostAdapter(
                userPage.postList,
                viewer,
                requireActivity(),
                this
            )

            binding.rvProfilePosts.layoutManager = layoutManager
            binding.rvProfilePosts.adapter = adapter

            registerForContextMenu(binding.rvProfilePosts);
        }
    }

    private fun showProfileInfo() {
        if (URLUtil.isValidUrl(userPage.banner)) {
            Picasso.get().load(userPage.banner).into(binding.ivProfileBanner)
        } else {
            Picasso.get().load(R.drawable.ic_blank_profile).into(binding.ivProfileBanner)
        }

        if (URLUtil.isValidUrl(userPage.profilePic)) {
            Picasso.get().load(userPage.profilePic).into(binding.ivProfilePicture)
        } else {
            Picasso.get().load(R.drawable.ic_blank_profile).into(binding.ivProfileBanner)
        }

        binding.tvProfileName.text = userPage.name
        binding.tvProfileDescription.text = userPage.description
    }

    private fun showTalentList() {
        binding.llTalentList.removeAllViews()

        binding.llTalentList.visibility = if (userPage.roles.isEmpty())
            View.GONE else View.VISIBLE

        userPage.roles.forEachIndexed { i, it ->
            val talentCard = layoutInflater.inflate(R.layout.card_talent, null)
            talentCard.findViewById<ImageView>(R.id.iv_talent_icon)
                .setImageResource(it.name.icon)
            talentCard.findViewById<TextView>(R.id.tv_talent_name).text = it.name.s

            binding.llTalentList.addView(talentCard)

            if (userPage.roles.size != i + 1) {
                val space = Space(context)

                val layoutParams = LinearLayout.LayoutParams(
                    32,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                space.layoutParams = layoutParams

                binding.llTalentList.addView(space)
            }
        }
    }

    private fun showBandInfo() {
        if (userPage.group != null && userPage.group.id != null) {

            if (URLUtil.isValidUrl(userPage.group.profilePic)) {
                Picasso.get().load(userPage.group.profilePic).into(binding.ivBandProfilePicture)
            } else {
                Picasso.get().load(R.drawable.ic_blank_profile).into(binding.ivBandProfilePicture)
            }

            binding.tvBandProfileName.text = userPage.group.name
        } else {
            binding.clProfileBandInfo.visibility = View.GONE
        }
    }

    private fun setupActionBar() {
        val activity = requireActivity() as AppCompatActivity

        activity.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        activity.supportActionBar!!.setDisplayShowCustomEnabled(true)

        if (!userPage.viewerProfile) {
            activity.supportActionBar!!.setCustomView(R.layout.action_bar_back)

            activity.findViewById<ImageView>(R.id.iv_back_button).setOnClickListener {
                activity.onBackPressed()
            }
        } else {
            activity.supportActionBar!!.setCustomView(R.layout.action_bar_settings)

            activity.findViewById<ImageView>(R.id.iv_settings_button).setOnClickListener {
            }
        }

        activity.findViewById<TextView>(R.id.tv_page_name).text = userPage.name
    }

    private fun setListeners() {
        binding.ivProfileBanner.setOnClickListener {
            ImageUtils.popupImage(
                binding.ivProfileBanner.drawable,
                this.requireView()
            )
        }

        binding.ivProfilePicture.setOnClickListener {
            ImageUtils.popupImage(
                binding.ivProfilePicture.drawable,
                this.requireView()
            )
        }

        binding.btEditProfile.setOnClickListener {
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            intent.putExtra(USER_PAGE_KEY, GsonUtils.INSTANCE.toJson(userPage))
            startActivityForResult(intent, PROFILE_EDIT_REQUEST_CODE)
        }

        binding.fabWritePost.setOnClickListener {
            val intent = Intent(requireActivity(), WritingActivity::class.java)
            intent.putExtra(
                VIEWER_KEY,
                GsonUtils.INSTANCE.toJson(viewer)
            )
            startActivityForResult(intent, POST_WRITING_REQUEST_CODE)
        }
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
            ORIGIN_POST_KEY,
            GsonUtils.INSTANCE.toJson(post)
        )
        intent.putExtra(
            ORIGIN_POST_POSITION_KEY,
            position
        )
        intent.putExtra(
            VIEWER_KEY,
            GsonUtils.INSTANCE.toJson(viewer)
        )
        startActivityForResult(intent, COMMENT_CREATION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PROFILE_EDIT_REQUEST_CODE &&
            resultCode == AppCompatActivity.RESULT_OK
        ) {
            userPage = GsonUtils.INSTANCE.fromJson(
                data!!.getStringExtra(USER_NEW_PROFILE_KEY),
                UserPage::class.java
            )

            showProfileInfo()
            showTalentList()
        } else if (requestCode == POST_WRITING_REQUEST_CODE &&
            resultCode == AppCompatActivity.RESULT_OK
        ) {
            val newPost = GsonUtils.INSTANCE.fromJson(
                data!!.getStringExtra(USER_NEW_POST_KEY),
                PostSimple::class.java
            )

            userPage.postList.add(0, newPost)
            adapter.notifyItemInserted(0)
            binding.rvProfilePosts.smoothScrollToPosition(0)
        } else if (requestCode == COMMENT_CREATION_REQUEST_CODE &&
            resultCode == AppCompatActivity.RESULT_OK
        ) {
            val position = data!!.getIntExtra(ORIGIN_POST_POSITION_KEY, 0)
            val newCommentAmount = data.getIntExtra(NEW_COMMENT_AMOUNT_KEY, 0)

            userPage.postList[position].commentCount = newCommentAmount
            adapter.notifyItemChanged(position)
        }
    }
}
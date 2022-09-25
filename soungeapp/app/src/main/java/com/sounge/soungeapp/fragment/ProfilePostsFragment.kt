package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.reflect.TypeToken
import com.sounge.soungeapp.adapter.PostAdapter
import com.sounge.soungeapp.data.PostSimple
import com.sounge.soungeapp.databinding.FragmentProfilePostsBinding
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.POST_LIST_KEY
import com.sounge.soungeapp.utils.GsonUtils

class ProfilePostsFragment : Fragment(), PostRelated {
    private lateinit var binding: FragmentProfilePostsBinding
    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val postList = GsonUtils.INSTANCE.fromJson<List<PostSimple>>(
            arguments?.getString(POST_LIST_KEY),
            object: TypeToken<List<PostSimple>>() {}.type
        )

        val layoutManager = LinearLayoutManager(requireActivity())

        binding = FragmentProfilePostsBinding.inflate(inflater, container, false)
        adapter = PostAdapter(postList, requireActivity(), this)

        binding.rvProfilePosts.layoutManager = layoutManager
        binding.rvProfilePosts.adapter = adapter

        return binding.root
    }

    override fun onLike(position: Int) {
        val post = adapter.getItem(position)
        post.likeCount++
        post.hasLiked = true
        adapter.notifyItemChanged(position, post)
    }

    override fun onUnlike(position: Int) {
        val post = adapter.getItem(position)
        post.likeCount--
        post.hasLiked = false
        adapter.notifyItemChanged(position, post)
    }
}
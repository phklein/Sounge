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
import com.sounge.soungeapp.utils.GsonUtils

class ProfilePostsFragment : Fragment() {
    private lateinit var binding: FragmentProfilePostsBinding
    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val postList = GsonUtils.INSTANCE.fromJson<List<PostSimple>>(
            arguments?.getString("postList"),
            object: TypeToken<List<PostSimple>>() {}.type
        )

        val layoutManager = LinearLayoutManager(requireActivity())

        binding = FragmentProfilePostsBinding.inflate(inflater, container, false)
        adapter = PostAdapter(postList, requireActivity())

        binding.rvProfilePosts.layoutManager = layoutManager
        binding.rvProfilePosts.adapter = adapter

        return binding.root
    }
}
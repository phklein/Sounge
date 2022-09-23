package com.sounge.soungeapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sounge.soungeapp.databinding.FragmentProfileTalentsBinding

class ProfileTalentsFragment : Fragment() {
    private lateinit var binding: FragmentProfileTalentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileTalentsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
package com.sounge.soungeapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.FragmentProfileBandBinding
import com.sounge.soungeapp.databinding.FragmentProfileBinding

class ProfileBandFragment : Fragment() {
    private lateinit var binding: FragmentProfileBandBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBandBinding.inflate(inflater, container, false)
        return binding.root
    }
}
package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sounge.soungeapp.databinding.FragmentPremiumInitialBinding

class PremiumFragmentInitial : Fragment() {
    private lateinit var binding: FragmentPremiumInitialBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPremiumInitialBinding.inflate(inflater)
        return binding.root
    }



}
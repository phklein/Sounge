package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sounge.soungeapp.databinding.FragmentPremiumInfoBinding


class PremiumInfoFragment : Fragment() {

    private lateinit var binding: FragmentPremiumInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPremiumInfoBinding.inflate(inflater)
        return binding.root
    }

}
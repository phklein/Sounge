package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.FragmentMatchBinding
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient


class MatchFragment : Fragment() {

    private lateinit var binding: FragmentMatchBinding

    private lateinit var userClient: UserClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchBinding.inflate(inflater, container, false)
        userClient = Retrofit.getInstance().create(UserClient::class.java)

        return binding.root
    }

}
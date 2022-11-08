package com.sounge.soungeapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.FragmentMatch2Binding
import com.sounge.soungeapp.databinding.FragmentMatchBinding
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient


class MatchFragment2 : Fragment() {

    private lateinit var binding: FragmentMatch2Binding

    private lateinit var userClient: UserClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatch2Binding.inflate(inflater, container, false)
        userClient = Retrofit.getInstance().create(UserClient::class.java)

        return binding.root
    }

}
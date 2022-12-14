package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.Fragment
import com.sounge.soungeapp.databinding.FragmentTuninInfoBinding
import com.sounge.soungeapp.response.UserMatch
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.squareup.picasso.Picasso


class MatchInfoFragment(private val userMatch: UserMatch) : Fragment() {

    private lateinit var binding: FragmentTuninInfoBinding

    private lateinit var userClient: UserClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTuninInfoBinding.inflate(inflater, container, false)
        userClient = Retrofit.getInstance().create(UserClient::class.java)

        setInfoUser()

        return binding.root
    }

    private fun setInfoUser() {
        binding.tvName.text = userMatch.name
        binding.tvAge.text = userMatch.age.toString()
        binding.tvDescription.text = userMatch.description

        if (URLUtil.isValidUrl(userMatch.profilePic)) {
            Picasso.get().load(userMatch.profilePic).into(binding.ivProfilePic)
        }
    }
}
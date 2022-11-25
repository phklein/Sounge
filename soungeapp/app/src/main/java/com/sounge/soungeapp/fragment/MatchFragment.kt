package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.Fragment
import com.sounge.soungeapp.data.UserMatch
import com.sounge.soungeapp.databinding.FragmentMatchBinding
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.squareup.picasso.Picasso


class MatchFragment(private val userMatch: UserMatch) : Fragment() {

    private lateinit var binding: FragmentMatchBinding

    private lateinit var userClient: UserClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchBinding.inflate(inflater, container, false)
        userClient = Retrofit.getInstance().create(UserClient::class.java)

        setInfoCard()

        return binding.root
    }

    private fun setInfoCard() {
        if (URLUtil.isValidUrl(userMatch.profilePic)) {
            Picasso.get().load(userMatch.profilePic).into(binding.ivProfilePic)
        }
    }

    fun getMatchObject(): UserMatch {
        return userMatch
    }
}
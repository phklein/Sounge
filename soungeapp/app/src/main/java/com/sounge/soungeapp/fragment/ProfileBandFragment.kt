package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.Fragment
import com.google.gson.reflect.TypeToken
import com.sounge.soungeapp.data.GroupSimple
import com.sounge.soungeapp.data.RoleSimple
import com.sounge.soungeapp.databinding.FragmentProfileBandBinding
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.BAND_INFO_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.IS_PROFILE_OWNER_KEY
import com.sounge.soungeapp.utils.GsonUtils
import com.squareup.picasso.Picasso

class ProfileBandFragment : Fragment() {
    private lateinit var binding: FragmentProfileBandBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bandInfo = GsonUtils.INSTANCE.fromJson(
            arguments?.getString(BAND_INFO_KEY), GroupSimple::class.java
        )

        val isProfileOwner = arguments?.getBoolean(IS_PROFILE_OWNER_KEY)

        binding = FragmentProfileBandBinding.inflate(inflater, container, false)

        setBandInfo(bandInfo, isProfileOwner)

        return binding.root
    }

    private fun setBandInfo(bandInfo: GroupSimple?, isProfileOwner: Boolean?) {
        if (bandInfo != null) {
            binding.clProfileBand.visibility = View.VISIBLE
            binding.llProfileNoBand.visibility = View.GONE

            if (URLUtil.isValidUrl(bandInfo.profilePic)) {
                Picasso.get().load(bandInfo.profilePic).into(binding.ivProfileBandPicture)
            }

            binding.tvProfileBandName.text = bandInfo.name
            binding.clProfileBand.setOnClickListener {
                // TODO: Onclick para ir para tela de perfil da banda
            }
        } else if (isProfileOwner!!) {
            binding.btGoToMatch.visibility = View.VISIBLE
            binding.btGoToMatch.setOnClickListener {
                // TODO: Onclick para ir para tela de match
            }
        }
    }
}
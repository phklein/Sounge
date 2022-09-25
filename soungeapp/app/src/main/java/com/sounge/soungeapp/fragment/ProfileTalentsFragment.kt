package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.reflect.TypeToken
import com.sounge.soungeapp.R
import com.sounge.soungeapp.data.RoleSimple
import com.sounge.soungeapp.databinding.FragmentProfileTalentsBinding
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.TALENT_LIST_KEY
import com.sounge.soungeapp.utils.GsonUtils


class ProfileTalentsFragment : Fragment() {
    private lateinit var binding: FragmentProfileTalentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val talentList = GsonUtils.INSTANCE.fromJson<List<RoleSimple>>(
            arguments?.getString(TALENT_LIST_KEY),
            object: TypeToken<List<RoleSimple>>() {}.type
        )

        binding = FragmentProfileTalentsBinding.inflate(inflater, container, false)
        showTalentList(talentList)

        return binding.root
    }

    private fun showTalentList(talentList: List<RoleSimple>) {
        talentList.forEach {
            val talentCard = layoutInflater.inflate(R.layout.card_talent, null)
            talentCard.findViewById<ImageView>(R.id.iv_talent_icon)
                .setImageResource(it.roleName.icon)
            talentCard.findViewById<TextView>(R.id.tv_talent_name).text = it.roleName.s

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            layoutParams.setMargins(24, 24, 24, 24)
            talentCard.layoutParams = layoutParams

            binding.fblTalentList.addView(talentCard)
        }
    }
}
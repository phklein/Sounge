package com.sounge.soungeapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        replaceFragment(ProfilePostsFragment(), DestinationFragment.POSTS)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.tvPostsOption.setOnClickListener {
            replaceFragment(ProfilePostsFragment(), DestinationFragment.POSTS)
        }

        binding.tvTalentsOption.setOnClickListener {
            replaceFragment(ProfileTalentsFragment(), DestinationFragment.TALENTS)
        }

        binding.tvBandOption.setOnClickListener {
            replaceFragment(ProfileBandFragment(), DestinationFragment.BAND)
        }
    }

    private fun replaceFragment(fragment: Fragment, destinationFragment: DestinationFragment) {
        val fragmentManager = childFragmentManager

        val currentFragment = fragmentManager.findFragmentByTag(fragment.javaClass.name)

        if (currentFragment != null && currentFragment.isVisible) {
            return
        }

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_profile, fragment)
        fragmentTransaction.commit()

        updateMenuColors(destinationFragment)
    }

    private fun updateMenuColors(destinationFragment: DestinationFragment) {
        val context = activity!!

        resetMenu(context)

        val option: TextView
        val optionUnderline: View

        when (destinationFragment) {
            DestinationFragment.POSTS -> {
                option = binding.tvPostsOption
                optionUnderline = binding.vPostsOptionUnderline
            }
            DestinationFragment.TALENTS -> {
                option = binding.tvTalentsOption
                optionUnderline = binding.vTalentsOptionUnderline
            }
            DestinationFragment.BAND -> {
                option = binding.tvBandOption
                optionUnderline = binding.vBandOptionUnderline
            }
        }

        option.setTextColor(ContextCompat.getColor(context, R.color.main_purple))
        optionUnderline.visibility = View.VISIBLE
    }

    private fun resetMenu(context: FragmentActivity) {
        binding.tvPostsOption.setTextColor(ContextCompat.getColor(context, R.color.light_gray))
        binding.vPostsOptionUnderline.visibility = View.INVISIBLE
        binding.tvTalentsOption.setTextColor(ContextCompat.getColor(context, R.color.light_gray))
        binding.vTalentsOptionUnderline.visibility = View.INVISIBLE
        binding.tvBandOption.setTextColor(ContextCompat.getColor(context, R.color.light_gray))
        binding.vBandOptionUnderline.visibility = View.INVISIBLE
    }
}

enum class DestinationFragment {
    POSTS, TALENTS, BAND
}
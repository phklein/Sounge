package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.sounge.soungeapp.R
import com.sounge.soungeapp.data.UserPage
import com.sounge.soungeapp.databinding.FragmentProfileBinding
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userClient: UserClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        userClient = Retrofit.getInstance().create(UserClient::class.java)

        setListeners()
        getProfileInfo()

        return binding.root
    }

    private fun getProfileInfo() {
        val callback = userClient.getUserPage(6, 2)

        callback.enqueue(object : Callback<UserPage> {
            override fun onResponse(call: Call<UserPage>, response: Response<UserPage>) {
                val body = response.body()

                if (body == null) {
                    showError()
                    return
                }

                setProfileInfo(body)

                val args = Bundle()
                args.putString("postList", GsonUtils.INSTANCE.toJson(body.postList))

                val postFragment = ProfilePostsFragment()
                postFragment.arguments = args

                replaceFragment(postFragment, DestinationFragment.POSTS)
            }

            override fun onFailure(call: Call<UserPage>, t: Throwable) {
                showError()
            }

            fun showError() {
                Toast.makeText(activity, "Erro ao carregar perfil", Toast.LENGTH_LONG).show()
                activity?.onBackPressed()
            }
        })
    }

    private fun setProfileInfo(body: UserPage) {
        Picasso.get().load(body.banner).into(binding.ivProfileBanner)
        Picasso.get().load(body.profilePic).into(binding.ivProfilePicture)
        binding.tvProfileName.text = body.name
        binding.tvProfileDescription.text = body.description
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
        val context = requireActivity()

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
package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.sounge.soungeapp.R
import com.sounge.soungeapp.data.GroupSimple
import com.sounge.soungeapp.data.PostSimple
import com.sounge.soungeapp.data.UserPage
import com.sounge.soungeapp.data.UserSimple
import com.sounge.soungeapp.databinding.FragmentProfileBinding
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userClient: UserClient
    private lateinit var postFragmentBackup: ProfilePostsFragment

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
        val postList = ArrayList<PostSimple>()
        postList.add(PostSimple(
            1,
            "Muito obrigado pelo show rapaziada, foi chave \n\n É ISSO!",
            "https://www.ofuxico.com.br/wp-content/uploads/2021/08/shrek-sessao-da-tarde-1.jpg",
            20,
            UserSimple(1, "Danielzinho do Rock", "https://yt3.ggpht.com/FSA44v5dG56FXl25kAuka8ceV5CtJa20l7oNhxpfMWSC_MDfrFWxG4GJFa3iRTWkfXDjfU8wt6Y=s88-c-k-c0x00ffffff-no-rj",
            true),
            GroupSimple(1, "TURMA DO ROCK", ""),
            10,
            20,
            true
        ))

        postList.add(PostSimple(
            2,
            "Acho que vou começar a cantar pagode.......",
            "",
            23,
            UserSimple(1, "Danielzinho do Rock", "",
                true),
            GroupSimple(1, "TURMA DO ROCK", ""),
            18372,
            20321,
            false
        ))

        val args = Bundle()
        args.putString("postList", GsonUtils.INSTANCE.toJson(postList))

        postFragmentBackup = ProfilePostsFragment()
        postFragmentBackup.arguments = args

        replaceFragment(postFragmentBackup, DestinationFragment.POSTS)

//        val callback = userClient.getUserPage(6, 2)
//
//        callback.enqueue(object : Callback<UserPage> {
//            override fun onResponse(call: Call<UserPage>, response: Response<UserPage>) {
//                val body = response.body()
//
//                if (body == null) {
//                    showError()
//                    return
//                }
//
//                setProfileInfo(body)
//
//                val args = Bundle()
//                args.putString("postList", GsonUtils.INSTANCE.toJson(body.postList))
//
//                val postFragment = ProfilePostsFragment()
//                postFragment.arguments = args
//
//                replaceFragment(postFragment, DestinationFragment.POSTS)
//            }
//
//            override fun onFailure(call: Call<UserPage>, t: Throwable) {
//                showError()
//            }
//
//            fun showError() {
//                Toast.makeText(activity, "Erro ao carregar perfil", Toast.LENGTH_LONG).show()
//                activity?.onBackPressed()
//            }
//        })
    }

    private fun setProfileInfo(body: UserPage) {
        Picasso.get().load(body.banner).into(binding.ivProfileBanner)
        Picasso.get().load(body.profilePic).into(binding.ivProfilePicture)
        binding.tvProfileName.text = body.name
        binding.tvProfileDescription.text = body.description
    }

    private fun setListeners() {
        binding.tvPostsOption.setOnClickListener {
            replaceFragment(postFragmentBackup, DestinationFragment.POSTS)
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
        val targetFragment = fragmentManager.findFragmentByTag(fragment.javaClass.name)

        if (targetFragment != null && targetFragment.isVisible) {
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
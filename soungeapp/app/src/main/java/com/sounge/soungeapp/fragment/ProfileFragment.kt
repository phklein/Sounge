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
import com.sounge.soungeapp.data.*
import com.sounge.soungeapp.databinding.FragmentProfileBinding
import com.sounge.soungeapp.enums.RoleName
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.BAND_INFO_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.IS_PROFILE_OWNER_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.POST_LIST_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.TALENT_LIST_KEY
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.sounge.soungeapp.utils.ImageUtils
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userClient: UserClient

    private val postFragment: ProfilePostsFragment = ProfilePostsFragment()
    private val talentsFragment: ProfileTalentsFragment = ProfileTalentsFragment()
    private val bandFragment: ProfileBandFragment = ProfileBandFragment()

    object Constants {
        const val POST_LIST_KEY = "postList"
        const val TALENT_LIST_KEY = "talentList"
        const val BAND_INFO_KEY = "bandInfo"
        const val IS_PROFILE_OWNER_KEY = "isProfileOwner"
    }

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
        val postArgs = Bundle()
        postArgs.putString(POST_LIST_KEY, GsonUtils.INSTANCE.toJson(mockPosts()))
        postFragment.arguments = postArgs

        val talentArgs = Bundle()
        talentArgs.putString(TALENT_LIST_KEY, GsonUtils.INSTANCE.toJson(mockTalents()))
        talentsFragment.arguments = talentArgs

        val bandArgs = Bundle()
        bandArgs.putString(BAND_INFO_KEY, GsonUtils.INSTANCE.toJson(mockBand()))
        bandArgs.putBoolean(IS_PROFILE_OWNER_KEY, true)
        bandFragment.arguments = bandArgs

        replaceFragment(postFragment, DestinationFragment.POSTS)

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

    private fun mockPosts(): ArrayList<PostSimple> {
        val postList = ArrayList<PostSimple>()
        postList.add(
            PostSimple(
                1,
                "Muito obrigado pelo show rapaziada, foi chave \n\n É ISSO!",
                "https://www.ofuxico.com.br/wp-content/uploads/2021/08/shrek-sessao-da-tarde-1.jpg",
                20,
                UserSimple(
                    1,
                    "Danielzinho do Rock",
                    "https://conteudo.imguol.com.br/c/entretenimento/58/2020/09/28/phil-claudio-gonzales-e-a-cara-do-chaves-1601293813371_v2_600x600.jpg",
                    true
                ),
                GroupSimple(1, "TURMA DO ROCK", ""),
                10,
                20,
                true
            )
        )

        postList.add(
            PostSimple(
                2,
                "Acho que vou começar a cantar pagode.......",
                "",
                78,
                UserSimple(
                    1,
                    "Danielzinho do Rock",
                    "https://conteudo.imguol.com.br/c/entretenimento/58/2020/09/28/phil-claudio-gonzales-e-a-cara-do-chaves-1601293813371_v2_600x600.jpg",
                    true
                ),
                GroupSimple(1, "TURMA DO ROCK", ""),
                12,
                1,
                false
            )
        )

        postList.add(
            PostSimple(
                3,
                "",
                "https://wallpaperaccess.com/full/1213672.jpg",
                563,
                UserSimple(
                    1,
                    "Danielzinho do Rock",
                    "https://conteudo.imguol.com.br/c/entretenimento/58/2020/09/28/phil-claudio-gonzales-e-a-cara-do-chaves-1601293813371_v2_600x600.jpg",
                    true
                ),
                GroupSimple(1, "TURMA DO ROCK", ""),
                923,
                23,
                false
            )
        )

        postList.add(
            PostSimple(
                4,
                "Hoje tomei o DIABO VERDE do TOGURO vou DESTRUIR TUDO na ACADEMIA \n HAHAHAHAHAHAHAHAHAHAHAHHAHAHAHAHAHAAHAHAHAH",
                "https://c.tenor.com/ZRHlOrai4F4AAAAM/toguro-lan%C3%A7ando-a-braba-toguro.gif",
                1500,
                UserSimple(
                    1,
                    "Danielzinho do Rock",
                    "https://conteudo.imguol.com.br/c/entretenimento/58/2020/09/28/phil-claudio-gonzales-e-a-cara-do-chaves-1601293813371_v2_600x600.jpg",
                    true
                ),
                GroupSimple(1, "TURMA DO ROCK", ""),
                239,
                342,
                false
            )
        )

        postList.add(
            PostSimple(
                5,
                "EU NÃO QUERO SABER DE MAIS NADAAAAAAAAA",
                "",
                29000,
                UserSimple(
                    1,
                    "Danielzinho do Rock",
                    "https://conteudo.imguol.com.br/c/entretenimento/58/2020/09/28/phil-claudio-gonzales-e-a-cara-do-chaves-1601293813371_v2_600x600.jpg",
                    true
                ),
                GroupSimple(1, "TURMA DO ROCK", ""),
                12,
                32,
                false
            )
        )
        return postList
    }

    private fun mockTalents(): ArrayList<RoleSimple> {
        val talentList = ArrayList<RoleSimple>()

        talentList.add(RoleSimple(1, RoleName.VOCALIST))
        talentList.add(RoleSimple(2, RoleName.GUITARIST))
        talentList.add(RoleSimple(3, RoleName.BASSPLAYER))
        talentList.add(RoleSimple(4, RoleName.DJ))
        talentList.add(RoleSimple(5, RoleName.ACCORDIONIST))
        talentList.add(RoleSimple(6, RoleName.CORNETPLAYER))
        talentList.add(RoleSimple(7, RoleName.DRUMMER))
        talentList.add(RoleSimple(8, RoleName.EKEYBOARDPLAYER))
        talentList.add(RoleSimple(9, RoleName.EGUITARIST))
        talentList.add(RoleSimple(10, RoleName.FLUTIST))
        talentList.add(RoleSimple(11, RoleName.PIANIST))
        talentList.add(RoleSimple(12, RoleName.SAXOPHONIST))
        talentList.add(RoleSimple(13, RoleName.OTHERS))

        return talentList
    }

    private fun mockBand(): GroupSimple {
        return GroupSimple(
            1,
            "Tropa do Rock",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSm2zSDYY03ED0I4kXbHKyYhCmHbFtfU1_FepUo3OBRomjb1rw_Jk1WglkwlygQCxV8JqA&usqp=CAU"
        )
    }

    private fun setProfileInfo(body: UserPage) {
        Picasso.get().load(body.banner).into(binding.ivProfileBanner)
        Picasso.get().load(body.profilePic).into(binding.ivProfilePicture)
        binding.tvProfileName.text = body.name
        binding.tvProfileDescription.text = body.description
    }

    private fun setListeners() {
        binding.ivProfileBanner.setOnClickListener {
            ImageUtils.popupImage(binding.ivProfileBanner.drawable, this.requireView())
        }

        binding.ivProfilePicture.setOnClickListener {
            ImageUtils.popupImage(binding.ivProfilePicture.drawable, this.requireView())
        }

        binding.tvPostsOption.setOnClickListener {
            replaceFragment(postFragment, DestinationFragment.POSTS)
        }

        binding.tvTalentsOption.setOnClickListener {
            replaceFragment(talentsFragment, DestinationFragment.TALENTS)
        }

        binding.tvBandOption.setOnClickListener {
            replaceFragment(bandFragment, DestinationFragment.BAND)
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
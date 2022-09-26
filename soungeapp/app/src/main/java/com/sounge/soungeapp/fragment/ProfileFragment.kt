package com.sounge.soungeapp.fragment

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.EditProfileActivity
import com.sounge.soungeapp.adapter.PostAdapter
import com.sounge.soungeapp.data.*
import com.sounge.soungeapp.databinding.FragmentProfileBinding
import com.sounge.soungeapp.enums.RoleName
import com.sounge.soungeapp.enums.SkillLevel
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.USER_PAGE_KEY
import com.sounge.soungeapp.listeners.PostEventListener
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.sounge.soungeapp.utils.ImageUtils
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment(), PostEventListener {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userPage: UserPage

    private lateinit var adapter: PostAdapter

    private lateinit var userClient: UserClient

    object Constants {
        const val USER_PAGE_KEY = "userPage"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        userClient = Retrofit.getInstance().create(UserClient::class.java)

        getProfileInfo()

        setListeners()

        return binding.root
    }

    private fun getProfileInfo() {
        userPage = mockProfile()

        setupRecyclerView()
        setProfileInfo()
    }

    private fun mockProfile(): UserPage {
        return UserPage(
            1,
            "Danielzinho do Rock",
            "https://conteudo.imguol.com.br/c/entretenimento/58/2020/09/28/phil-claudio-gonzales-e-a-cara-do-chaves-1601293813371_v2_600x600.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRe2dvZilCtcjeyvMg0u9dJpsOPOQOvCxQaA&usqp=CAU",
            true,
            "Eu gosto de tocar guitarra mesmo, rock mesmo tlgd? Fumo cigarro e bebo pinga no gargalo, não ligo!!!!!!",
            true,
            SkillLevel.INTERMEDIATE,
            mockBand(),
            ArrayList(),
            mockTalents(),
            32,
            true,
            mockPosts()
        )
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
                2_590_000,
                39_000,
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
                120_000,
                1_908_000,
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
                1_000,
                1_000,
                false
            )
        )

        postList.add(
            PostSimple(
                4,
                "Hoje tomei o DIABO VERDE do TOGURO vou DESTRUIR TUDO na ACADEMIA \nHAHAHAHAHAHAHAHAHAHAHAHHAHAHAHAHAHAAHAHAHAH",
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
                0,
                0,
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

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())

        adapter = PostAdapter(userPage.postList, requireActivity(), this)

        binding.rvProfilePosts.layoutManager = layoutManager
        binding.rvProfilePosts.adapter = adapter

        registerForContextMenu(binding.rvProfilePosts);
    }

    private fun setProfileInfo() {
        Picasso.get().load(userPage.banner).into(binding.ivProfileBanner)

        if (URLUtil.isValidUrl(userPage.profilePic)) {
            Picasso.get().load(userPage.profilePic).into(binding.ivProfilePicture)
        } else {
            Picasso.get().load(R.drawable.ic_blank_profile).into(binding.ivProfilePicture)
        }

        binding.tvProfileName.text = userPage.name
        binding.tvProfileDescription.text = userPage.description

        showTalentList()
    }

    private fun showTalentList() {
        userPage.roles.forEachIndexed { i, it ->
            val talentCard = layoutInflater.inflate(R.layout.card_talent, null)
            talentCard.findViewById<ImageView>(R.id.iv_talent_icon)
                .setImageResource(it.roleName.icon)
            talentCard.findViewById<TextView>(R.id.tv_talent_name).text = it.roleName.s

            binding.vTalentList.addView(talentCard)

            if (userPage.roles.size == i + 1) {
                val space = Space(context)

                val layoutParams = LinearLayout.LayoutParams(
                    8,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                space.layoutParams = layoutParams

                binding.vTalentList.addView(space)
            }
        }
    }

    private fun setListeners() {
        binding.ivProfileBanner.setOnClickListener {
            ImageUtils.popupImage(
                binding.ivProfileBanner.drawable,
                this.requireView()
            )
        }

        binding.ivProfilePicture.setOnClickListener {
            ImageUtils.popupImage(
                binding.ivProfilePicture.drawable,
                this.requireView()
            )
        }

        binding.btEditProfile.setOnClickListener {
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            intent.putExtra(USER_PAGE_KEY, GsonUtils.INSTANCE.toJson(userPage))
            startActivity(intent)
        }
    }

    override fun onLike(position: Int) {
        val post = adapter.getItem(position)
        post.likeCount++
        post.hasLiked = true
        adapter.notifyItemChanged(position, post)
    }

    override fun onUnlike(position: Int) {
        val post = adapter.getItem(position)
        post.likeCount--
        post.hasLiked = false
        adapter.notifyItemChanged(position, post)
    }
}
package com.sounge.soungeapp.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.CommentActivity
import com.sounge.soungeapp.actitivy.EditProfileActivity
import com.sounge.soungeapp.actitivy.EditProfileActivity.Constants.USER_NEW_PROFILE_KEY
import com.sounge.soungeapp.actitivy.WritingActivity
import com.sounge.soungeapp.actitivy.WritingActivity.Constants.USER_NEW_POST_KEY
import com.sounge.soungeapp.adapter.PostAdapter
import com.sounge.soungeapp.data.*
import com.sounge.soungeapp.databinding.FragmentProfileBinding
import com.sounge.soungeapp.enums.RoleName
import com.sounge.soungeapp.enums.SkillLevel
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.COMMENT_CREATION_REQUEST_CODE
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.NEW_COMMENT_AMOUNT_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.ORIGIN_POST_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.ORIGIN_POST_POSITION_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.POST_WRITING_REQUEST_CODE
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.PROFILE_EDIT_REQUEST_CODE
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.USER_PAGE_KEY
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.USER_SIMPLE_KEY
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
        const val PROFILE_EDIT_REQUEST_CODE = 1
        const val POST_WRITING_REQUEST_CODE = 2
        const val COMMENT_CREATION_REQUEST_CODE = 3

        const val ORIGIN_POST_KEY = "originPost"
        const val ORIGIN_POST_POSITION_KEY = "originPostPosition"
        const val USER_PAGE_KEY = "userPage"
        const val USER_SIMPLE_KEY = "userSimple"
        const val NEW_COMMENT_AMOUNT_KEY = "newCommentAmount"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        userClient = Retrofit.getInstance().create(UserClient::class.java)
        getProfileInfo()

        hideViewsIfViewerNotOwner()

        setupActionBar()
        setListeners()

        return binding.root
    }

    private fun hideViewsIfViewerNotOwner() {
        if (!userPage.isViewerProfile) {
            binding.btEditProfile.visibility = View.GONE
            binding.fabWritePost.visibility = View.GONE
        }
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
            false,
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
                null,
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
                null,
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
                null,
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
                null,
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
                null,
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

        // TODO: Receber viewer da main activity
        adapter = PostAdapter(
            userPage.postList,
            requireActivity(),
            this
        )

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
        showBandInfo()
    }

    private fun showTalentList() {
        binding.llTalentList.removeAllViews()

        binding.llTalentList.visibility = if (userPage.roles.isEmpty())
            View.GONE else View.VISIBLE

        userPage.roles.forEachIndexed { i, it ->
            val talentCard = layoutInflater.inflate(R.layout.card_talent, null)
            talentCard.findViewById<ImageView>(R.id.iv_talent_icon)
                .setImageResource(it.roleName.icon)
            talentCard.findViewById<TextView>(R.id.tv_talent_name).text = it.roleName.s

            binding.llTalentList.addView(talentCard)

            if (userPage.roles.size != i + 1) {
                val space = Space(context)

                val layoutParams = LinearLayout.LayoutParams(
                    32,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                space.layoutParams = layoutParams

                binding.llTalentList.addView(space)
            }
        }
    }

    private fun showBandInfo() {
        if (userPage.group != null && userPage.group.id != null) {

            if (URLUtil.isValidUrl(userPage.group.profilePic)) {
                Picasso.get().load(userPage.group.profilePic).into(binding.ivBandProfilePicture)
            } else {
                Picasso.get().load(R.drawable.ic_blank_profile).into(binding.ivBandProfilePicture)
            }

            binding.tvBandProfileName.text = userPage.group.name
        } else {
            binding.clProfileBandInfo.visibility = View.GONE
        }
    }

    private fun setupActionBar() {
        val activity = requireActivity() as AppCompatActivity

        activity.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        activity.supportActionBar!!.setDisplayShowCustomEnabled(true)
        activity.supportActionBar!!.setCustomView(R.layout.action_bar_back)

        activity.findViewById<ImageView>(R.id.iv_back_button).setOnClickListener {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
            activity.onBackPressed()
        }

        activity.findViewById<TextView>(R.id.tv_page_name).text = userPage.name
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
            startActivityForResult(intent, PROFILE_EDIT_REQUEST_CODE)
        }

        binding.fabWritePost.setOnClickListener {
            val intent = Intent(requireActivity(), WritingActivity::class.java)
            intent.putExtra(
                USER_SIMPLE_KEY,
                GsonUtils.INSTANCE.toJson(
                    UserSimple(
                        userPage.id, userPage.name, userPage.profilePic, userPage.isLeader
                    )
                )
            )
            startActivityForResult(intent, POST_WRITING_REQUEST_CODE)
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

    override fun onClickComment(post: PostSimple, position: Int) {
        val intent = Intent(context, CommentActivity::class.java)
        intent.putExtra(
            ORIGIN_POST_KEY,
            GsonUtils.INSTANCE.toJson(post)
        )
        intent.putExtra(
            ORIGIN_POST_POSITION_KEY,
            position
        )
        intent.putExtra(
            USER_SIMPLE_KEY,
            GsonUtils.INSTANCE.toJson(
                UserSimple(
                    1,
                    "Danielzinho do Rock",
                    "https://conteudo.imguol.com.br/c/entretenimento/58/2020/09/28/phil-claudio-gonzales-e-a-cara-do-chaves-1601293813371_v2_600x600.jpg",
                    true
                )
            )
        )
        startActivityForResult(intent, COMMENT_CREATION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PROFILE_EDIT_REQUEST_CODE &&
            resultCode == AppCompatActivity.RESULT_OK
        ) {
            userPage = GsonUtils.INSTANCE.fromJson(
                data!!.getStringExtra(USER_NEW_PROFILE_KEY),
                UserPage::class.java
            )

            setProfileInfo()
            showTalentList()
        } else if (requestCode == POST_WRITING_REQUEST_CODE &&
            resultCode == AppCompatActivity.RESULT_OK
        ) {
            val newPost = GsonUtils.INSTANCE.fromJson(
                data!!.getStringExtra(USER_NEW_POST_KEY),
                PostSimple::class.java
            )

            userPage.postList.add(0, newPost)
            adapter.notifyItemInserted(0)
            binding.rvProfilePosts.smoothScrollToPosition(0)
        } else if (requestCode == COMMENT_CREATION_REQUEST_CODE &&
            resultCode == AppCompatActivity.RESULT_OK
        ) {
            val position = data!!.getIntExtra(ORIGIN_POST_POSITION_KEY, 0)
            val newCommentAmount = data.getIntExtra(NEW_COMMENT_AMOUNT_KEY, 0)

            userPage.postList[position].commentCount = newCommentAmount
            adapter.notifyItemInserted(position)
        }
    }
}
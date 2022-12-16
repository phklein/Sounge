package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.gson.reflect.TypeToken
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.sounge.soungeapp.R
import com.sounge.soungeapp.adapter.GroupMatchAdapter
import com.sounge.soungeapp.adapter.UserMatchAdapter
import com.sounge.soungeapp.databinding.FragmentTuninBinding
import com.sounge.soungeapp.enums.*
import com.sounge.soungeapp.response.*
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


class TuninFragment : Fragment() {

    private lateinit var binding: FragmentTuninBinding

    private lateinit var userClient: UserClient

    private lateinit var userLogin: UserLogin
    private lateinit var user: UserMatch

    private lateinit var userCardList: UserCardList
    private lateinit var groupCardList: GroupCardList
    private lateinit var userMatchAdapter: UserMatchAdapter
    private lateinit var groupMatchAdapter: GroupMatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTuninBinding.inflate(inflater, container, false)
        userClient = Retrofit.getInstance().create(UserClient::class.java)

        userLogin = GsonUtils.INSTANCE.fromJson(
            SharedPreferencesUtils.get(
                requireActivity(),
                SharedPreferencesUtils.Constants.USER_INFO_PREFS,
                SharedPreferencesUtils.Constants.USER_LOGIN_KEY
            ),
            UserLogin::class.java
        )

        user = GsonUtils.INSTANCE.fromJson(
            SharedPreferencesUtils.get(
                requireActivity(),
                "userLog",
                "user"
            ),
            UserMatch::class.java
        )

        if (user.leader == true) {
            groupCardList = GroupCardList(mutableListOf())
            userCardList = UserCardList(GsonUtils.INSTANCE.fromJson(
                SharedPreferencesUtils.get(
                    requireActivity(),
                    "userCardMatch",
                    "userCard"
                ),
                object : TypeToken<MutableList<UserMatch>>() {}.type)
            )

            createAdapter("users")
        } else if (user.leader == false) {
            userCardList = UserCardList(mutableListOf())
            groupCardList = GroupCardList(GsonUtils.INSTANCE.fromJson(
                SharedPreferencesUtils.get(
                    requireActivity(),
                    "groupCardMatch",
                    "groupCard"
                ),
                object : TypeToken<MutableList<GroupMatch>>() {}.type)
            )

            createAdapter("groups")
        }

        return binding.root
    }

    private fun createAdapter(typeFind: String) {
        val flingContainer: SwipeFlingAdapterView = binding.root.findViewById(R.id.frame)

        if (typeFind == "users") {
            groupMatchAdapter = GroupMatchAdapter(requireActivity(), R.layout.item, mutableListOf())
            userMatchAdapter = UserMatchAdapter(
                requireActivity(),
                R.layout.item,
                userCardList.cards
            )

            flingContainer.adapter = userMatchAdapter

            flingContainer.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
                override fun removeFirstObjectInAdapter() {
                    userCardList.cards.removeAt(0)
                    userMatchAdapter.notifyDataSetChanged()

                    if (userCardList.cards.size == 0) {
                        binding.tvWarningCards.text = "Lista vazia!"
                    }
                }

                override fun onLeftCardExit(dataObject: Any?) {
//                    unlikeUser(dataObject, "user")
                }

                override fun onRightCardExit(dataObject: Any?) {
                    likeUser(dataObject, "user")
                }

                override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                    userMatchAdapter.notifyDataSetChanged()
                }

                override fun onScroll(scrollProgressPercent: Float) {}
            })

            flingContainer.setOnItemClickListener { itemPosition, dataObject ->
                replaceFragment(
                    TuninInfoFragment(
                        userCardList.cards[0],
                        null,
                        null,
                        true
                    )
                )
            }

            binding.ivRecuseBtn.setOnClickListener {
                flingContainer.topCardListener.selectLeft()
            }

            binding.ivMatchBtn.setOnClickListener {
                flingContainer.topCardListener.selectRight()
            }
        } else if (typeFind == "groups") {
            userMatchAdapter = UserMatchAdapter(requireActivity(), R.layout.item, mutableListOf())
            groupMatchAdapter = GroupMatchAdapter(
                requireActivity(),
                R.layout.item,
                groupCardList.cards
            )

            flingContainer.adapter = groupMatchAdapter

            flingContainer.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
                override fun removeFirstObjectInAdapter() {
                    groupCardList.cards.removeAt(0)
                    groupMatchAdapter.notifyDataSetChanged()

                    if (groupCardList.cards.size == 0) {
                        binding.tvWarningCards.text = "Lista vazia!"
                    }
                }

                override fun onLeftCardExit(dataObject: Any?) {
//                    unlikeUser(dataObject, "group")
                }

                override fun onRightCardExit(dataObject: Any?) {
                    likeUser(dataObject, "group")
                }

                override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                    groupMatchAdapter.notifyDataSetChanged()
                }

                override fun onScroll(scrollProgressPercent: Float) {}
            })

            flingContainer.setOnItemClickListener { itemPosition, dataObject ->
                replaceFragment(
                    TuninInfoFragment(
                        null,
                        groupCardList.cards[0],
                        null,
                        true
                    )
                )
            }

            binding.ivRecuseBtn.setOnClickListener {
                flingContainer.topCardListener.selectLeft()
            }

            binding.ivMatchBtn.setOnClickListener {
                flingContainer.topCardListener.selectRight()
            }
        }
    }

    private fun unlikeUser(dataObject: Any?, type: String) {
        if (type == "user") {
            val userMatch = dataObject as UserMatch

            userClient.unlikeUser(userLogin.id, userMatch.id).enqueue(
                object : retrofit2.Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        when (response.code()) {
                            404 -> Toast.makeText(
                                requireActivity(),
                                "Erro ao procurar usuário",
                                Toast.LENGTH_SHORT
                            ).show()
                            405 -> Toast.makeText(
                                requireActivity(),
                                "Erro ao processar deslike",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(
                            requireActivity(),
                            "Erro inesperado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        } else {
            val groupMatch = dataObject as GroupMatch

            userClient.unlikeUser(userLogin.id, groupMatch.leaderId).enqueue(
                object : retrofit2.Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        when (response.code()) {
                            404 -> Toast.makeText(
                                requireActivity(),
                                "Erro ao procurar usuário",
                                Toast.LENGTH_SHORT
                            ).show()
                            405 -> Toast.makeText(
                                requireActivity(),
                                "Erro ao processar deslike",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(
                            requireActivity(),
                            "Erro inesperado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }

    private fun likeUser(dataObject: Any?, type: String) {
        if (type == "user") {
            val userMatch = dataObject as UserMatch

            userClient.likeUser(userLogin.id, userMatch.id).enqueue(
                object : retrofit2.Callback<Boolean> {
                    override fun onResponse(
                        call: Call<Boolean>,
                        response: Response<Boolean>
                    ) {
                        when (response.code()) {
                            201 -> {
                                if (response.body()!!) {
                                    Toast.makeText(
                                        requireActivity(),
                                        "Parabéns, Você está sintonizado(a) com o(a) ${userMatch.name}!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            404 -> Toast.makeText(
                                requireActivity(),
                                "Erro ao procurar usuário",
                                Toast.LENGTH_SHORT
                            ).show()
                            405 -> Toast.makeText(
                                requireActivity(),
                                "Erro ao processar like",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Toast.makeText(
                            requireActivity(),
                            "Erro inesperado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        } else {
            val groupMatch = dataObject as GroupMatch

            userClient.likeUser(userLogin.id, groupMatch.leaderId).enqueue(
                object : retrofit2.Callback<Boolean> {
                    override fun onResponse(
                        call: Call<Boolean>,
                        response: Response<Boolean>
                    ) {
                        when (response.code()) {
                            201 -> {
                                if (response.body()!!) {
                                    Toast.makeText(
                                        requireActivity(),
                                        "Parabéns, Você está sintonizado(a) com a banda ${groupMatch.name}!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            404 -> Toast.makeText(
                                requireActivity(),
                                "Erro ao procurar usuário",
                                Toast.LENGTH_SHORT
                            ).show()
                            405 -> Toast.makeText(
                                requireActivity(),
                                "Erro ao processar like",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Toast.makeText(
                            requireActivity(),
                            "Erro inesperado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val currentFragment = requireFragmentManager().findFragmentByTag(fragment.javaClass.name)
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

        if (currentFragment != null && currentFragment.isVisible) {
            return
        }

        transaction.replace(R.id.fl_main, fragment, fragment.javaClass.name)
        transaction.commitAllowingStateLoss()
    }

    private fun mockCardsList(): ArrayList<UserMatch> {
        val cardsList = ArrayList<UserMatch>(
            listOf(
                UserMatch(
                    1,
                    "Daniel do Rock",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0QQDZiJHvjPBEHdxr0Cg8ar0pLHw61eO7QQ&usqp=CAU",
                    false,
                    Sex.MALE,
                    State.SP,
                    "",
                    "Estou em busca de uma nova banda compromissada!",
                    true,
                    SkillLevel.BEGINNER,
                    null,
                    listOf(GenreSimple(1, GenreName.CLASSICAL), GenreSimple(2, GenreName.INDIE)),
                    listOf(RoleSimple(1, RoleName.DRUMMER), RoleSimple(2, RoleName.ACCORDIONIST)),
                    20,
                    20.0,
                    12.0,
                    true,
                    ""
                ),
                UserMatch(
                    1,
                    "Aleff do Samba",
                    "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=ting&dpr=2&h=650&w=940",
                    true,
                    Sex.MALE,
                    State.SP,
                    "",
                    "Estou em busca de uma nova banda compromissada!",
                    true,
                    SkillLevel.BEGINNER,
                    GroupSimple(1, "Grupo de Samba", "https://i.ytimg.com/vi/4rbZYxCosIg/maxresdefault.jpg"),
                    listOf(GenreSimple(1, GenreName.CLASSICAL), GenreSimple(2, GenreName.INDIE)),
                    listOf(RoleSimple(1, RoleName.EKEYBOARDPLAYER)),
                    20,
                    20.0,
                    12.0,
                    true,
                    ""
                ),
                UserMatch(
                    1,
                    "Terceiro",
                    "https://upload.wikimedia.org/wikipedia/pt/7/79/Benedict_Cumberbatch_como_Doutor_Estranho.jpeg",
                    true,
                    Sex.MALE,
                    State.SP,
                    "",
                    "Estou em busca de uma nova banda compromissada!",
                    true,
                    SkillLevel.BEGINNER,
                    GroupSimple(1, "Grupo de Samba", "https://i.ytimg.com/vi/4rbZYxCosIg/maxresdefault.jpg"),
                    listOf(GenreSimple(1, GenreName.CLASSICAL), GenreSimple(2, GenreName.INDIE)),
                    listOf(RoleSimple(1, RoleName.EKEYBOARDPLAYER)),
                    20,
                    20.0,
                    12.0,
                    true,
                    ""
                )
            )
        )

        return cardsList
    }


}
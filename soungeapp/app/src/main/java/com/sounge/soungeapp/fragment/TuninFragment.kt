package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.sounge.soungeapp.R
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

    private lateinit var cardsList: CardsList
    private lateinit var userMatchAdapter: UserMatchAdapter

    private lateinit var itemLayout: View

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

        println(userLogin)

        itemLayout = inflater.inflate(R.layout.item, container, false)

        findCardsList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun findCardsList() {
        userClient.findMatchList(userLogin.id, 1000, 0).enqueue(
            object : retrofit2.Callback<MutableList<UserMatch>> {
                override fun onResponse(
                    call: Call<MutableList<UserMatch>>,
                    response: Response<MutableList<UserMatch>>
                ) {
                    when (response.code()) {
                        200 -> {
                            cardsList = CardsList(response.body()!!)

                            userMatchAdapter = UserMatchAdapter(
                                requireActivity(),
                                R.layout.item,
                                cardsList.cards
                            )

                            val flingContainer: SwipeFlingAdapterView = binding.root.findViewById(R.id.frame)

                            flingContainer.adapter = userMatchAdapter

                            flingContainer.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
                                override fun removeFirstObjectInAdapter() {
                                    cardsList.cards.removeAt(0)
                                    userMatchAdapter.notifyDataSetChanged()

                                    if (cardsList.cards.size == 0) {
                                        binding.tvWarningCards.text = "Lista vazia!"
                                    }
                                }

                                override fun onLeftCardExit(dataObject: Any?) {}

                                override fun onRightCardExit(dataObject: Any?) {
                                    val userMatch = dataObject as UserMatch
                                    likeUser(userMatch.id)
                                }

                                override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                                    userMatchAdapter.notifyDataSetChanged()
                                }

                                override fun onScroll(scrollProgressPercent: Float) {}
                            })

                            flingContainer.setOnItemClickListener { itemPosition, dataObject ->
                                replaceFragment(
                                    TuninInfoFragment(cardsList.cards[0], null)
                                )
                            }

                            binding.ivRecuseBtn.setOnClickListener {
                                flingContainer.topCardListener.selectLeft()
                            }

                            binding.ivMatchBtn.setOnClickListener {
                                flingContainer.topCardListener.selectRight()
                            }
                        }
                        204 -> {
                            Toast.makeText(requireActivity(), "CODE 204", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<MutableList<UserMatch>>, t: Throwable) {
                    Toast.makeText(
                        requireActivity(),
                        "Erro inesperado...",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }

    private fun likeUser(likedId: Long) {
        userClient.likeUser(userLogin.id, likedId).enqueue(
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
                            "Erro ao processar like",
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
                    "https://veja.abril.com.br/wp-content/uploads/2016/06/alx_maria_bethania3_original.jpeg",
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
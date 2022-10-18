package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sounge.soungeapp.adapter.CardStackAdapter
import com.sounge.soungeapp.data.*
import com.sounge.soungeapp.databinding.FragmentMatchBinding
import com.sounge.soungeapp.enums.*
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.yuyakaido.android.cardstackview.*


class MatchFragment : Fragment(), CardStackListener {
    private lateinit var binding: FragmentMatchBinding
    private lateinit var cardsList: CardsList

    private lateinit var adapter: CardStackAdapter

    private lateinit var userClient: UserClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchBinding.inflate(inflater, container, false)
        userClient = Retrofit.getInstance().create(UserClient::class.java)

        getMatchInfo()

        return binding.root
    }

    private fun getMatchInfo() {
        cardsList = mockCardsList()

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Left)
            .setDuration(Duration.Normal.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()

        adapter = CardStackAdapter(cardsList.cards, requireActivity(), this)

        binding.csvCardStack.layoutManager = CardStackLayoutManager(context)
        binding.csvCardStack.adapter = adapter

        (binding.csvCardStack.layoutManager as CardStackLayoutManager).setSwipeAnimationSetting(setting)
    }

    private fun mockCardsList(): CardsList {
        val cardsList = ArrayList<UserMatch>(
            listOf(
                UserMatch(
                    1,
                    "Danielzinho do Rock",
                    "https://veja.abril.com.br/wp-content/uploads/2016/06/alx_maria_bethania3_original.jpeg",
                    false,
                    Sex.MALE,
                    State.SP,
                    "",
                    "",
                    true,
                    SkillLevel.BEGINNER,
                    null,
                    mockGenres(),
                    mockRoles(),
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
                    "",
                    true,
                    SkillLevel.BEGINNER,
                    mockGroup(),
                    mockGenres(),
                    mockRoles(),
                    20,
                    20.0,
                    12.0,
                    true,
                    ""
                )
            )
        )

        return CardsList(cardsList)
    }

    private fun mockGroup(): GroupSimple {
        return GroupSimple(
            1,
            "Grupo do Samba",
            "https://i.ytimg.com/vi/4rbZYxCosIg/maxresdefault.jpg"
        )
    }

    private fun mockGenres(): List<GenreSimple> {
        return listOf(
            GenreSimple(1, GenreName.CLASSICAL),
            GenreSimple(2, GenreName.INDIE)
        )
    }

    private fun mockRoles(): List<RoleSimple> {
        return listOf(
            RoleSimple(1, RoleName.DRUMMER),
            RoleSimple(2, RoleName.ACCORDIONIST)
        )
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(direction)
            .setDuration(Duration.Normal.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()

        val manager = CardStackLayoutManager(requireActivity())

        manager.setSwipeAnimationSetting(setting)
        binding.csvCardStack.swipe()
    }

    override fun onCardSwiped(direction: Direction?) {
        TODO("Not yet implemented")
    }

    override fun onCardRewound() {
        TODO("Not yet implemented")
    }

    override fun onCardCanceled() {
        TODO("Not yet implemented")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        TODO("Not yet implemented")
    }
}
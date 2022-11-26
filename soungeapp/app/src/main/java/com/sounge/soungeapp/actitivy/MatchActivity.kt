package com.sounge.soungeapp.actitivy

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.sounge.soungeapp.R
import com.sounge.soungeapp.adapter.ViewPagerAdapter
import com.sounge.soungeapp.databinding.ActivityMatchBinding
import com.sounge.soungeapp.enums.*
import com.sounge.soungeapp.fragment.MatchFragment
import com.sounge.soungeapp.fragment.MatchInfoFragment
import com.sounge.soungeapp.response.*
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import retrofit2.Call
import retrofit2.Response


class MatchActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    private lateinit var binding: ActivityMatchBinding

    private lateinit var adapter: ViewPagerAdapter
    private lateinit var fragments: ArrayList<MatchFragment>

    private lateinit var cardsList: CardsList
    private lateinit var userClient: UserClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userClient = Retrofit.getInstance().create(UserClient::class.java)

        viewPager = findViewById(R.id.viewPager)

        findCardsList()

        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                updateMatchInfo(adapter.getItem(position).getMatchObject())
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        binding.ivMoreInfoButton.setOnClickListener {
            replaceFragment(MatchInfoFragment(adapter.getItem(viewPager.currentItem).getMatchObject()))
        }
    }

    private fun createFragments(): ArrayList<MatchFragment> {
        val fragments = ArrayList<MatchFragment>()

        cardsList.cards.forEach {
            val matchFragment = MatchFragment(it)

            fragments.add(matchFragment)
        }

        return fragments
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
            updateMatchInfo(adapter.getItem(viewPager.currentItem).getMatchObject())
        }
    }

    private fun findCardsList() {
        userClient.findMatchList(28, 1000).enqueue(
            object : retrofit2.Callback<MutableList<UserMatch>> {
                override fun onResponse(
                    call: Call<MutableList<UserMatch>>,
                    response: Response<MutableList<UserMatch>>
                ) {
                    when (response.code()) {
                        200 -> cardsList = CardsList(response.body()!!)
                        204 -> cardsList = CardsList(mockCardsList())
                    }
                    setupRecyclerView()
                }

                override fun onFailure(call: Call<MutableList<UserMatch>>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Erro inesperado...",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }

    private fun setupRecyclerView() {
        fragments = createFragments()

        adapter = ViewPagerAdapter(fragments, this)
        viewPager.adapter = adapter
    }

    private fun updateMatchInfo(matchObject: UserMatch) {
        findViewById<TextView>(R.id.tvName).text = matchObject.name

        val firstTalent : TextView = findViewById(R.id.tvFirstTalent)
        val secondTalent : TextView = findViewById(R.id.tvSecondTalent)

        firstTalent.visibility = View.VISIBLE
        secondTalent.visibility = View.VISIBLE

        when (matchObject.roles.size) {
            0 -> {
                firstTalent.visibility = View.INVISIBLE
                secondTalent.visibility = View.INVISIBLE
            }
            1 -> {
                firstTalent.text = matchObject.roles[0].roleName.s
                secondTalent.visibility = View.INVISIBLE
            }
            else -> {
                firstTalent.text = matchObject.roles[0].roleName.s
                secondTalent.text = matchObject.roles[1].roleName.s
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val currentFragment = fragmentManager.findFragmentByTag(fragment.javaClass.name)

        if (currentFragment != null && currentFragment.isVisible) {
            return
        }

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_match, fragment, fragment.javaClass.name)
        fragmentTransaction.commitAllowingStateLoss()
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
                    "Estou em busca de uma nova banda compromissada!",
                    true,
                    SkillLevel.BEGINNER,
                    mockGroup(),
                    mockGenres(),
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
}
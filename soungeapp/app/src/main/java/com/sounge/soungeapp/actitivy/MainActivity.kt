package com.sounge.soungeapp.actitivy

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.MainActivity.Constants.PROFILE_OWNER_ID_KEY
import com.sounge.soungeapp.databinding.ActivityMainBinding
import com.sounge.soungeapp.fragment.*
import com.sounge.soungeapp.response.CardsList
import com.sounge.soungeapp.response.Page
import com.sounge.soungeapp.response.UserLogin
import com.sounge.soungeapp.response.UserMatch
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils.Constants.USER_INFO_PREFS
import com.sounge.soungeapp.utils.SharedPreferencesUtils.Constants.USER_LOGIN_KEY
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var cardsList: CardsList

    private lateinit var userClient: UserClient

    private lateinit var viewer: UserLogin

    object Constants {
        const val PROFILE_OWNER_ID_KEY = "profileOwnerId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        userClient = Retrofit.getInstance().create(UserClient::class.java)
        setContentView(binding.root)
        setupActionBar()

        viewer = GsonUtils.INSTANCE.fromJson(
            SharedPreferencesUtils.get(
                this,
                USER_INFO_PREFS,
                USER_LOGIN_KEY),
            UserLogin::class.java
        )

        findCardsList(viewer.id)

        replaceFragment(FeedFragment())

        // TODO: Substituir fragments conforme for criando
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mi_home -> replaceFragment(FeedFragment())
                R.id.mi_search -> replaceFragment(ProfileFragment())
                R.id.mi_match -> {
                    val args = Bundle()
                    args.putLong(PROFILE_OWNER_ID_KEY, viewer.id)

                    val fragment = TuninFragment()
                    fragment.arguments = args
                    
                    replaceFragment(fragment)
                }
                R.id.mi_notifications -> replaceFragment(ProfileFragment())
                R.id.mi_profile -> {
                    val args = Bundle()
                    args.putLong(PROFILE_OWNER_ID_KEY, viewer.id)

                    val fragment = ProfileFragment()
                    fragment.arguments = args

                    replaceFragment(fragment)
                }
                else -> {}
            }
            true
        }
    }

    private fun setupActionBar() {
        // TODO: Mudar para actionbar com menu lateral
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val currentFragment = fragmentManager.findFragmentByTag(fragment.javaClass.name)

        if (currentFragment != null && currentFragment.isVisible) {
            return
        }

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_main, fragment, fragment.javaClass.name)
        fragmentTransaction.commit()
    }

    private fun findCardsList(ownerId: Long) {
        userClient.findMatchList(ownerId, 1000, 0).enqueue(
            object : retrofit2.Callback<Page<UserMatch>> {
                override fun onResponse(
                    call: Call<Page<UserMatch>>,
                    response: Response<Page<UserMatch>>
                ) {
                    when (response.code()) {
                        200 -> {
                            cardsList = CardsList(response.body()!!.content)
                            SharedPreferencesUtils.put(
                                this@MainActivity, "cardMatch", "card", GsonUtils.INSTANCE.toJson(cardsList.cards)
                            )
                        }
                        204 -> {
                            Toast.makeText(this@MainActivity, "CODE 204", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Page<UserMatch>>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Erro inesperado...",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }
}
package com.sounge.soungeapp.actitivy

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.MainActivity.Constants.PROFILE_OWNER_ID_KEY
import com.sounge.soungeapp.response.UserLogin
import com.sounge.soungeapp.databinding.ActivityMainBinding
import com.sounge.soungeapp.fragment.FeedFragment
import com.sounge.soungeapp.fragment.ProfileFragment
import com.sounge.soungeapp.utils.GsonUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils.Constants.USER_INFO_PREFS
import com.sounge.soungeapp.utils.SharedPreferencesUtils.Constants.USER_LOGIN_KEY

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewer: UserLogin

    object Constants {
        const val PROFILE_OWNER_ID_KEY = "profileOwnerId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        viewer = UserLogin(
            27,
            "Danielzinho do Rock",
            "https://conteudo.imguol.com.br/c/entretenimento/58/2020/09/28/phil-claudio-gonzales-e-a-cara-do-chaves-1601293813371_v2_600x600.jpg",
            true,
            0,
            0
        )

        // TODO: Botar esse código na hora do login, colocando oq retornar do banco
        SharedPreferencesUtils.put(
            this, USER_INFO_PREFS, USER_LOGIN_KEY,
            GsonUtils.INSTANCE.toJson(viewer)
        )

        replaceFragment(FeedFragment())

        // TODO: Substituir fragments conforme for criando
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mi_home -> replaceFragment(FeedFragment())
                R.id.mi_search -> replaceFragment(ProfileFragment())
                R.id.mi_match -> replaceFragment(ProfileFragment())
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
}
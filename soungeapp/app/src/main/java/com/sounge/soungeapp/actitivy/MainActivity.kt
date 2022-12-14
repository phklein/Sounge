package com.sounge.soungeapp.actitivy

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.MainActivity.Constants.PROFILE_OWNER_ID_KEY
import com.sounge.soungeapp.databinding.ActivityMainBinding
import com.sounge.soungeapp.fragment.*
import com.sounge.soungeapp.response.UserLogin
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

        viewer = GsonUtils.INSTANCE.fromJson(
            SharedPreferencesUtils.get(
                this,
                USER_INFO_PREFS,
                USER_LOGIN_KEY),
            UserLogin::class.java
        )

        replaceFragment(FeedFragment())

        // TODO: Substituir fragments conforme for criando
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mi_home -> replaceFragment(FeedFragment())
                R.id.mi_search -> replaceFragment(ProfileFragment())
                R.id.mi_match -> {
                    val args = Bundle()
                    args.putLong(PROFILE_OWNER_ID_KEY, viewer.id)

                    val fragment = ContactFragment()
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
}
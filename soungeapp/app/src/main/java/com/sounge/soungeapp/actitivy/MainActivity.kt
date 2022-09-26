package com.sounge.soungeapp.actitivy

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.ActivityMainBinding
import com.sounge.soungeapp.fragment.ProfileFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        // TODO: Substituir para o fragment do feed
        replaceFragment(ProfileFragment())

        // TODO: Substituir fragments conforme for criando
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mi_home -> replaceFragment(ProfileFragment())
                R.id.mi_search -> replaceFragment(ProfileFragment())
                R.id.mi_match -> replaceFragment(ProfileFragment())
                R.id.mi_notifications -> replaceFragment(ProfileFragment())
                R.id.mi_profile -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }
    }

    private fun setupActionBar() {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_main)

        findViewById<ImageView>(R.id.iv_back_button).setOnClickListener {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            onBackPressed()
        }
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
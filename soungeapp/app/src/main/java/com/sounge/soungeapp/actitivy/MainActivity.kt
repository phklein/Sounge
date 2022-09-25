package com.sounge.soungeapp.actitivy

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
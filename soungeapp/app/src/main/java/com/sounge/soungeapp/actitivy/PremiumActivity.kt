package com.sounge.soungeapp.actitivy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sounge.soungeapp.databinding.ActivityPremiumBinding
import com.sounge.soungeapp.fragment.PremiumFragmentInitial

class PremiumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPremiumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniciarTela()


    }

    private fun iniciarTela() {
        supportFragmentManager
            .beginTransaction()
            .add(
                binding.FragmentContainerView.id,
                PremiumFragmentInitial()
            ).commit()
    }
}
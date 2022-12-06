package com.sounge.soungeapp.actitivy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.ActivityPremiumBinding
import com.sounge.soungeapp.fragment.PremiumFragmentInitial
import com.sounge.soungeapp.fragment.PremiumInfoFragment

class PremiumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPremiumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniciarTela()

        binding.buttonPremiumAvanc.setOnClickListener {
            binding.buttonPremiumAvanc.setText(getText(R.string.avancar))
            trocarFragment()
        }

    }

    private fun iniciarTela() {
        supportFragmentManager
            .beginTransaction()
            .add(
                binding.FragmentContainerView.id,
                PremiumFragmentInitial()
            ).commit()
    }


    private fun trocarFragment() {
        val transaction = supportFragmentManager
            .beginTransaction()
        val container = binding.FragmentContainerView.id

        transaction.replace(container, PremiumInfoFragment())
        transaction.commit()

    }
}
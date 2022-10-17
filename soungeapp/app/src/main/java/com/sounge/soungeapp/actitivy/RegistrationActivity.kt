package com.sounge.soungeapp.actitivy

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private var items = arrayOf("Escolha sua categoria","Músico", "DJ", "Banda / Artista Solo", "Serviços", "Locais")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(this, R.layout.styler_spinner, items)
        binding.menuSpinner.setAdapter(adapter)

        validatorFinalizar()

    }

    private fun validatorFinalizar(){
        val botaoFinalizar = binding.finalizarButon

        botaoFinalizar.setOnClickListener {
            val intent = Intent(this, SuccessfulRegistrationActivity::class.java)
            startActivity(intent)
        }

    }



}


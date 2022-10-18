package com.sounge.soungeapp.actitivy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.ActivityRegistrationPrimaryBinding

class ActivityRegistrationPrimary : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationPrimaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationPrimaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun nextRegistration(view: View){
        val editor = getSharedPreferences("USER", MODE_PRIVATE).edit()
        editor.putString("name", binding.etNome.text.toString())
        editor.putString("email", binding.etEmail.text.toString())
        editor.putString("senha", binding.etSenha.text.toString())
        editor.apply()
        startActivity(Intent(baseContext, ActivityRegistrationSecundary::class.java))
    }

    fun rollBack(view: View){
        startActivity(Intent(baseContext, LoginActivity::class.java))
    }
}
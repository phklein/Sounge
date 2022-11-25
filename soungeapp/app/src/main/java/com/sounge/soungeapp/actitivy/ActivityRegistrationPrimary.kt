package com.sounge.soungeapp.actitivy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
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

    fun nextRegistration(view: View) {
        val nome: String = binding.etNome.text.toString()
        val email: String = binding.etEmail.text.toString()
        val senha: String = binding.etSenha.text.toString()
        val confirmarSenha: String = binding.etRepetirSenha.text.toString()

        if (isCampoVazio(nome)) {
            binding.etNome.error = "Campos vazio"
        } else if (!isEmailValido(email)) {
            binding.etEmail.error = "Email inválido"
        } else if (isCampoVazio(senha)) {
            binding.etSenha.error = "Campo Vazio"
        } else if (isCampoVazio(confirmarSenha) ) {
            binding.etRepetirSenha.error = "Campo Vazio"
        } else if(confirmarSenha != senha){
            binding.etRepetirSenha.error = "Senhas diferentes"
        } else {
            val editor = getSharedPreferences("USER", MODE_PRIVATE).edit()
            editor.putString("name", binding.etNome.text.toString())
            editor.putString("email", binding.etEmail.text.toString())
            editor.putString("senha", binding.etSenha.text.toString())
            editor.apply()

            startActivity(Intent(baseContext, ActivityRegistrationSecundary::class.java))
        }


    }


    fun isCampoVazio(valor: String): Boolean {
        val resultado: Boolean = (TextUtils.isEmpty(valor) || valor.trim().isEmpty())
        return resultado
    }

    fun isEmailValido(email: String): Boolean {
        val resultado: Boolean = (!isCampoVazio(email) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return resultado
    }



    fun rollBack(view: View) {
        startActivity(Intent(baseContext, LoginActivity::class.java))
    }
}
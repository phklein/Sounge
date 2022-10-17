package com.sounge.soungeapp.actitivy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.ActivitySuccessfulRegistrationBinding

class SuccessfulRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessfulRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessfulRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //validatorComecar()

    }

//    private fun validatorComecar(){
//        val botaoComecar = binding.comecarProcurar
//
//        botaoComecar.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//
//    }
}
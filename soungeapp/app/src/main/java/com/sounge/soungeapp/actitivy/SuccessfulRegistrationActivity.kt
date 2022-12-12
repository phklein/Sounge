package com.sounge.soungeapp.actitivy

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sounge.soungeapp.response.LoginResponse
import com.sounge.soungeapp.response.SaveUsers
import com.sounge.soungeapp.databinding.ActivitySuccessfulRegistrationBinding
import com.sounge.soungeapp.enums.GenreName
import com.sounge.soungeapp.enums.RoleName
import com.sounge.soungeapp.enums.SkillLevel
import com.sounge.soungeapp.enums.State
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import retrofit2.Call
import retrofit2.Response

class SuccessfulRegistrationActivity : AppCompatActivity() {

    private val retrofit = Retrofit.getInstance()
    private lateinit var binding: ActivitySuccessfulRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessfulRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        validatorComecar()

    }

    private fun validatorComecar() {
        val botaoComecar = binding.comecarProcurar

        botaoComecar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }


}
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
            cadastrar()
        }

    }

    private fun cadastrar() {
        val editor = getSharedPreferences("USER", MODE_PRIVATE)
        val name = editor.getString("name", "")
        val email = editor.getString("email", "")
        val password = editor.getString("senha", "")
        val state = editor.getString("estado", "ta dando erro aqui ó")
        val city = editor.getString("cidade", "")
        val likedGenres = editor.getString("style", "")
        val roles = editor.getString("category", "")
        val skillLevel = editor.getString("level", "")
        val body = SaveUsers(
            name = name, email = email,
            password = password, state = State.valueOf(state.toString()), city = city, sex = null,
            birthDate = null, phone = null, description = null,
            likedGenres = listOf(GenreName.values().find { it.s == likedGenres }),
            roles = listOf(RoleName.values().find { it.s == roles }),
            skillLevel = SkillLevel.values().find { it.s == skillLevel}
        )

        val intent = Intent(this, LoginActivity::class.java)
        val authRequest = retrofit.create(UserClient::class.java)

        authRequest.save(body).enqueue(
            object : retrofit2.Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            baseContext, "Name: ${response.body()?.name}",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            baseContext, "Cadastro não realizado",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(
                        baseContext, "DEU MUITO RUIM",
                        Toast.LENGTH_LONG
                    ).show()
                }


            })
    }
}
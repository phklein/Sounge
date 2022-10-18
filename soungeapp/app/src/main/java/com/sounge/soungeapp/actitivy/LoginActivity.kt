package com.sounge.soungeapp.actitivy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sounge.soungeapp.R
import com.sounge.soungeapp.data.LoginRequest
import com.sounge.soungeapp.data.LoginResponse
import com.sounge.soungeapp.data.UserLogin
import com.sounge.soungeapp.databinding.ActivityLoginBinding
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginActivity : AppCompatActivity() {

    private val retrofit = Retrofit.getInstance()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entrarButon.setOnClickListener {
            login()
        }
    }


    private fun login() {
        val email = binding.inserirEmail.text.toString()
        val password = binding.inserirSenha.text.toString()
        val body = LoginRequest(email, password)

        val authRequest = retrofit.create(UserClient::class.java)

        authRequest.login(body).enqueue(
            object : retrofit2.Callback<LoginResponse>{
                override fun onResponse(
                    call:Call<LoginResponse>,
                    response: Response<LoginResponse>
                ){
                    if(response.isSuccessful){
                        Toast.makeText(baseContext, "Name: ${response.body()?.name}",
                            Toast.LENGTH_LONG).show()
                        val mensagemDeSucesso = binding.mensagemDeErro
                        mensagemDeSucesso.text = getText(R.string.login_sucess)
                        mensagemDeSucesso.setTextColor(getColor(R.color.light_green))
                    } else{
                        val mensagemDeErro = binding.mensagemDeErro
                        mensagemDeErro.text = getText(R.string.login_error)
                        mensagemDeErro.setTextColor(getColor(R.color.red))
                    }

                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    val mensagemDeErro = binding.mensagemDeErro
                    mensagemDeErro.text = getText(R.string.login_error)
                    mensagemDeErro.setTextColor(getColor(R.color.red))
                }

            })
    }


}
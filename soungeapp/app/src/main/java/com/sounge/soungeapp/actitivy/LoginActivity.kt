package com.sounge.soungeapp.actitivy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sounge.soungeapp.R
import com.sounge.soungeapp.response.LoginRequest
import com.sounge.soungeapp.response.LoginResponse
import com.sounge.soungeapp.databinding.ActivityLoginBinding
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val context: Context = this
    private val retrofit = Retrofit.getInstance()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entrarButon.setOnClickListener {
            login()
        }

        binding.cadastrese.setOnClickListener{
            cadastro()
        }
    }

    private fun cadastro(){
        startActivity(Intent(baseContext, ActivityRegistrationPrimary::class.java))
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

                        SharedPreferencesUtils.put(context,
                            SharedPreferencesUtils.Constants.USER_INFO_PREFS,
                            SharedPreferencesUtils.Constants.USER_LOGIN_KEY,
                            GsonUtils.INSTANCE.toJson(response.body()!!))

                        startActivity(Intent(context, MainActivity::class.java))

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
package com.sounge.soungeapp.actitivy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.sounge.soungeapp.databinding.ActivityRegistrationTertiaryBinding
import com.sounge.soungeapp.enums.GenreName
import com.sounge.soungeapp.enums.RoleName
import com.sounge.soungeapp.enums.SkillLevel
import com.sounge.soungeapp.enums.State
import com.sounge.soungeapp.response.LoginResponse
import com.sounge.soungeapp.response.SaveUsers
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.sounge.soungeapp.utils.MusicUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils.Constants.USER_INFO_PREFS
import com.sounge.soungeapp.utils.SharedPreferencesUtils.Constants.USER_LOGIN_KEY
import retrofit2.Call
import retrofit2.Response


class ActivityRegistrationTertiary : AppCompatActivity() {

    private val context: Context = this
    private lateinit var binding: ActivityRegistrationTertiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationTertiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemSelectedSpinner(binding.dpStyle, MusicUtils.getStylesAll(), binding.tvStyle)
        itemSelectedSpinner(binding.dpCategory, MusicUtils.getCategoryAll(), binding.tvCategory)
        itemSelectedSpinner(binding.dpLevel, MusicUtils.getLevelAll(), binding.tvLevel)
    }

    private fun itemSelectedSpinner(
        spinner: Spinner,
        array: Array<String>,
        textView: TextView
    ) {
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            array
        )
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                textView.text = array[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    fun nextRegistration(view: View) {
        cadastrar()
    }

    private fun cadastrar() {
        val editor = getSharedPreferences("USER", MODE_PRIVATE)
        val name = editor.getString("name", "")
        val email = editor.getString("email", "")
        val password = editor.getString("senha", "")
        val state = editor.getString("estado", "ta dando erro aqui ó")
        val city = editor.getString("cidade", "")
        val likedGenres = binding.tvStyle.text.toString()
        val roles = binding.tvCategory.text.toString()
        val skillLevel = binding.tvLevel.text.toString()
        val body = SaveUsers(
            name = name, email = email,
            password = password, state = State.valueOf(state.toString()), city = city, sex = null,
            birthDate = null, phone = null, description = null,
            likedGenres = listOf(GenreName.values().find { it.s == likedGenres }),
            roles = listOf(RoleName.values().find { it.s == roles }),
            skillLevel = SkillLevel.values().find { it.s == skillLevel }
        )

        val intent = Intent(this, SuccessfulRegistrationActivity::class.java)
        val authRequest = Retrofit.getInstance().create(UserClient::class.java)

        authRequest.save(body).enqueue(
            object : retrofit2.Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.code() == 201) {
                        Toast.makeText(
                            baseContext, "Name: ${response.body()?.name}",
                            Toast.LENGTH_LONG
                        ).show()
                        SharedPreferencesUtils.put(context,
                            USER_INFO_PREFS, USER_LOGIN_KEY,
                            GsonUtils.INSTANCE.toJson(response.body()!!))
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

    fun rollBack(view: View) {
        startActivity(Intent(baseContext, LoginActivity::class.java))
    }

}
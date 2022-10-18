package com.sounge.soungeapp.actitivy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.sounge.soungeapp.R
import com.sounge.soungeapp.models.States
import com.sounge.soungeapp.models.Town
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.services.StatesService
import com.sounge.soungeapp.services.TownService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class ActivityRegistrationSecundary : AppCompatActivity() {

    private val retrofitStates = Retrofit.getInstanceIBGE().create(StatesService::class.java)
    private val retrofitTown = Retrofit.getInstanceIBGE().create(TownService::class.java)
    private val states = mutableListOf<String>()
    private val towns = mutableListOf<String>()
    private val statesObject = mutableListOf<States>()
    private lateinit var eTdocument: EditText
    private lateinit var dPtown: Spinner
    private lateinit var dPstate: Spinner
    private lateinit var btnProx: Button
    private lateinit var tvStates: TextView
    private lateinit var tvTown: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_secundary)

        eTdocument = findViewById(R.id.et_document)
        dPtown = findViewById(R.id.dp_town)
        dPstate = findViewById(R.id.dp_state)
        btnProx = findViewById(R.id.btn_next)
        tvStates = findViewById(R.id.tv_state)
        tvTown = findViewById(R.id.tv_town)
        states.add("")
        getStates()
        dPstate.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, states)
        dPstate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tvStates.text = states[position]
                getTown()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }



    }

    fun getStates(){
        retrofitStates.getStates().enqueue(object: Callback<List<States>> {
            override fun onResponse(call: Call<List<States>>, response: Response<List<States>>) {
                if (response.isSuccessful){
                    response.body()?.forEach { state ->
                        statesObject.add(state)
                        states.add(state.nome)
                    }
                } else {
                    Toast.makeText(baseContext, "não conseguimos encontrar estados", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<States>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun getTown(){
        towns.clear()
        var idState = 0
        statesObject.forEach { state ->
            if(tvStates.text.toString() == state.nome){
                idState = state.id
            }
        }
        retrofitTown.getTown(idState).enqueue(object: Callback<List<Town>>{
            override fun onResponse(call: Call<List<Town>>, response: Response<List<Town>>) {
                if (response.isSuccessful){
                    response.body()?.forEach { town ->
                        towns.add(town.nome)
                    }
                } else {
                    Toast.makeText(baseContext, "não conseguimos encontrar cidades", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Town>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
        towns.add("")
        dPtown.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, towns)
        dPtown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tvTown.text = towns[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    fun nextRegistration(view: View){
        val editor = getSharedPreferences("USER", MODE_PRIVATE).edit()
        editor.putString("cpf", eTdocument.text.toString())
        editor.putString("estado", tvStates.text.toString())
        editor.putString("cidade", tvTown.text.toString())
        editor.apply()
        startActivity(Intent(baseContext, RegistrationActivity::class.java))
    }

    fun rollBack(view: View){
        startActivity(Intent(baseContext, LoginActivity::class.java))
    }

}
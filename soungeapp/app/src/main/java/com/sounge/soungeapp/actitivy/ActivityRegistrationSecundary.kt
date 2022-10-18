package com.sounge.soungeapp.actitivy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.ActivityRegistrationSecundaryBinding
import com.sounge.soungeapp.databinding.ActivityRegistrationTertiaryBinding
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
    private lateinit var binding: ActivityRegistrationSecundaryBinding
    private val states = mutableListOf<String>()
    private val towns = mutableListOf<String>()
    private val statesObject = mutableListOf<States>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationSecundaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        states.add("")
        getStates()
        itemSelectedSpinner(binding.dpState, states, binding.tvState)
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
            if(binding.tvState.text.toString() == state.nome){
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
        itemSelectedSpinner(binding.dpTown, towns,binding.tvTown)
    }

    fun nextRegistration(view: View){
        val editor = getSharedPreferences("USER", MODE_PRIVATE).edit()
        editor.putString("cpf", binding.etDocument.text.toString())
        editor.putString("estado", binding.tvState.text.toString())
        editor.putString("cidade", binding.tvTown.text.toString())
        editor.apply()
        startActivity(Intent(baseContext, ActivityRegistrationTertiary::class.java))
    }

    fun rollBack(view: View){
        startActivity(Intent(baseContext, LoginActivity::class.java))
    }

    private fun itemSelectedSpinner(
        spinner: Spinner,
        array: MutableList<String>,
        textView: TextView
    ){
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            array
        )
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                textView.text = array[position]
                if(spinner == binding.dpState){
                    getTown()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}
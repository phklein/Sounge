package com.sounge.soungeapp.actitivy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.sounge.soungeapp.databinding.ActivityRegistrationTertiaryBinding
import com.sounge.soungeapp.utils.MusicUtils


class ActivityRegistrationTertiary : AppCompatActivity() {

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
        val editor = getSharedPreferences("USER", MODE_PRIVATE).edit()
        editor.putString("style", binding.tvStyle.text.toString())
        editor.putString("category", binding.tvCategory.text.toString())
        editor.putString("level", binding.tvLevel.text.toString())
        editor.apply()
        startActivity(Intent(baseContext, SuccessfulRegistrationActivity::class.java))
    }

    fun rollBack(view: View) {
        startActivity(Intent(baseContext, LoginActivity::class.java))
    }

}
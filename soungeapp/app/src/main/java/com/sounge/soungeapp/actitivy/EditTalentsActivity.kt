package com.sounge.soungeapp.actitivy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.reflect.TypeToken
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.EditProfileActivity.Constants.USER_TALENTS_KEY
import com.sounge.soungeapp.actitivy.EditTalentsActivity.Constants.TALENTS_TO_ADD_KEY
import com.sounge.soungeapp.actitivy.EditTalentsActivity.Constants.TALENTS_TO_REMOVE_KEY
import com.sounge.soungeapp.data.RoleSimple
import com.sounge.soungeapp.databinding.ActivityEditTalentsBinding
import com.sounge.soungeapp.enums.RoleName
import com.sounge.soungeapp.utils.GsonUtils

class EditTalentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTalentsBinding
    private lateinit var userTalentList: List<RoleSimple>
    private lateinit var otherTalentList: List<RoleSimple>

    private lateinit var talentsToAdd: MutableList<RoleSimple>
    private lateinit var talentsToRemove: MutableList<RoleSimple>

    object Constants {
        const val TALENTS_TO_ADD_KEY = "talentsToAdd"
        const val TALENTS_TO_REMOVE_KEY = "talentsToRemove"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTalentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userTalentList = GsonUtils.INSTANCE.fromJson(
            intent.getStringExtra(USER_TALENTS_KEY),
            object : TypeToken<List<RoleSimple>>() {}.type
        )

        showUserTalentList()

        otherTalentList = mockTalents()

        showOtherTalentList()

        talentsToAdd = ArrayList()
        talentsToRemove = ArrayList()

        setupActionBar()
    }

    private fun setupActionBar() {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_edit)

        findViewById<TextView>(R.id.tv_action_cancel).setOnClickListener {
            onBackPressed()
        }

        findViewById<TextView>(R.id.tv_action_save).setOnClickListener {
            val intent = Intent();

            intent.putExtra(
                TALENTS_TO_ADD_KEY,
                GsonUtils.INSTANCE.toJson(talentsToAdd)
            )

            intent.putExtra(
                TALENTS_TO_REMOVE_KEY,
                GsonUtils.INSTANCE.toJson(talentsToRemove)
            )

            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun showUserTalentList() {
        userTalentList.forEach {
            val talentCard = layoutInflater.inflate(R.layout.card_talent, null)
            talentCard.findViewById<ImageView>(R.id.iv_talent_icon)
                .setImageResource(it.roleName.icon)
            talentCard.findViewById<TextView>(R.id.tv_talent_name).text = it.roleName.s

            val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            layoutParams.setMargins(24, 24, 24, 24)
            talentCard.layoutParams = layoutParams
            talentCard.tag = R.drawable.bg_purple_round_fill;

            talentCard.setOnClickListener { v ->
                changeCardBackground(v)

                if (talentsToRemove.contains(it)) {
                    talentsToRemove.remove(it)
                } else {
                    talentsToRemove.add(it)
                }
            }

            binding.fblMyTalents.addView(talentCard)
        }
    }

    private fun changeCardBackground(v: View) {
        if (v.tag == R.drawable.bg_purple_round_fill) {
            v.tag = R.drawable.bg_purple_round_outline;
            v.setBackgroundResource(R.drawable.bg_purple_round_outline)
        } else {
            v.tag = R.drawable.bg_purple_round_fill;
            v.setBackgroundResource(R.drawable.bg_purple_round_fill)
        }
    }

    private fun mockTalents(): List<RoleSimple> {
        val talentList = ArrayList<RoleSimple>()

        RoleName.values().forEach {
            if (!userTalentList.any { t -> t.roleName == it }) {
                talentList.add(RoleSimple(1, it))
            }
        }

        return talentList
    }

    private fun showOtherTalentList() {
        otherTalentList.forEach {
            val talentCard = layoutInflater.inflate(R.layout.card_talent, null)
            talentCard.findViewById<ImageView>(R.id.iv_talent_icon)
                .setImageResource(it.roleName.icon)
            talentCard.findViewById<TextView>(R.id.tv_talent_name).text = it.roleName.s

            val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            layoutParams.setMargins(24, 24, 24, 24)
            talentCard.layoutParams = layoutParams
            talentCard.setBackgroundResource(R.drawable.bg_purple_round_outline)
            talentCard.tag = R.drawable.bg_purple_round_outline;

            talentCard.setOnClickListener { v ->
                changeCardBackground(v)

                if (talentsToAdd.contains(it)) {
                    talentsToAdd.remove(it)
                } else {
                    talentsToAdd.add(it)
                }
            }

            binding.fblOtherTalents.addView(talentCard)
        }
    }
}
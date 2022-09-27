package com.sounge.soungeapp.actitivy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.reflect.TypeToken
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.EditTalentsActivity.Constants.TALENTS_TO_ADD_KEY
import com.sounge.soungeapp.actitivy.EditTalentsActivity.Constants.TALENTS_TO_REMOVE_KEY
import com.sounge.soungeapp.actitivy.EditProfileActivity.Constants.USER_NEW_PROFILE_KEY
import com.sounge.soungeapp.actitivy.EditProfileActivity.Constants.USER_TALENTS_KEY
import com.sounge.soungeapp.data.RoleSimple
import com.sounge.soungeapp.data.UserPage
import com.sounge.soungeapp.databinding.ActivityEditProfileBinding
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.USER_PAGE_KEY
import com.sounge.soungeapp.utils.GsonUtils
import com.squareup.picasso.Picasso


class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var userPage: UserPage

    private lateinit var talentsToAdd: List<RoleSimple>
    private lateinit var talentsToRemove: List<RoleSimple>

    object Constants {
        const val USER_TALENTS_KEY = "userTalents"
        const val USER_NEW_PROFILE_KEY = "userNewProfile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPage = GsonUtils.INSTANCE.fromJson(
            intent.getStringExtra(USER_PAGE_KEY),
            UserPage::class.java
        )

        setupActionBar()
        setCurrentInfo()
        setListeners()
    }

    private fun setupActionBar() {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_edit)

        findViewById<TextView>(R.id.tv_action_cancel).setOnClickListener {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            onBackPressed()
        }

        findViewById<TextView>(R.id.tv_action_save).setOnClickListener {
            val newName = binding.etProfileName.text.toString()
            val newDescription = binding.etProfileDescription.text.toString()

            // TODO: Enviar ok se conseguir salvar no banco
            userPage.name = newName
            userPage.description = newDescription

            val intent = Intent()

            intent.putExtra(
                USER_NEW_PROFILE_KEY,
                GsonUtils.INSTANCE.toJson(userPage)
            )

            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun setCurrentInfo() {
        Picasso.get().load(userPage.banner).into(binding.ivEditProfileBanner)

        if (URLUtil.isValidUrl(userPage.profilePic)) {
            Picasso.get().load(userPage.profilePic).into(binding.ivEditProfilePicture)
        } else {
            Picasso.get().load(R.drawable.ic_blank_profile).into(binding.ivEditProfilePicture)
        }

        binding.etProfileName.setText(userPage.name)
        binding.etProfileDescription.setText(userPage.description)

        showTalentList()
    }

    private fun showTalentList() {
        binding.llEditTalentList.removeAllViews()

        userPage.roles.forEachIndexed { i, it ->
            val talentCard = layoutInflater.inflate(R.layout.card_talent, null)
            talentCard.findViewById<ImageView>(R.id.iv_talent_icon)
                .setImageResource(it.roleName.icon)
            talentCard.findViewById<TextView>(R.id.tv_talent_name).text = it.roleName.s

            binding.llEditTalentList.addView(talentCard)

            if (userPage.roles.size != i + 1) {
                val space = Space(this)

                val layoutParams = LinearLayout.LayoutParams(
                    32,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                space.layoutParams = layoutParams

                binding.llEditTalentList.addView(space)
            }
        }
    }

    private fun setListeners() {
        binding.ivEditProfileTalents.setOnClickListener {
            val intent = Intent(this, EditTalentsActivity::class.java)
            intent.putExtra(USER_TALENTS_KEY, GsonUtils.INSTANCE.toJson(userPage.roles))
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            talentsToAdd = GsonUtils.INSTANCE.fromJson(
                data?.getStringExtra(TALENTS_TO_ADD_KEY),
                object : TypeToken<List<RoleSimple>>() {}.type
            )

            talentsToRemove = GsonUtils.INSTANCE.fromJson(
                data?.getStringExtra(TALENTS_TO_REMOVE_KEY),
                object : TypeToken<List<RoleSimple>>() {}.type
            )

            userPage.roles.addAll(talentsToAdd)
            userPage.roles.removeAll(talentsToRemove)

            showTalentList()
        }
    }
}
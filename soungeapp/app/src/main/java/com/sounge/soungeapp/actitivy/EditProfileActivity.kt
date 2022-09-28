package com.sounge.soungeapp.actitivy

import android.content.Intent
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.reflect.TypeToken
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.EditProfileActivity.Constants.USER_NEW_PROFILE_KEY
import com.sounge.soungeapp.actitivy.EditProfileActivity.Constants.USER_TALENTS_KEY
import com.sounge.soungeapp.actitivy.EditTalentsActivity.Constants.TALENTS_TO_ADD_KEY
import com.sounge.soungeapp.actitivy.EditTalentsActivity.Constants.TALENTS_TO_REMOVE_KEY
import com.sounge.soungeapp.response.RoleSimple
import com.sounge.soungeapp.response.UserPage
import com.sounge.soungeapp.databinding.ActivityEditProfileBinding
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.USER_PAGE_KEY
import com.sounge.soungeapp.request.UpdateProfile
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    private lateinit var userPage: UserPage

    private lateinit var userClient: UserClient

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

        userClient = Retrofit.getInstance().create(UserClient::class.java)

        setupActionBar()
        setCurrentInfo()
        setListeners()
    }

    private fun setupActionBar() {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_edit)

        findViewById<TextView>(R.id.tv_action_cancel).setOnClickListener {
            onBackPressed()
        }

        findViewById<TextView>(R.id.tv_action_save).setOnClickListener {
            val newName = binding.etProfileName.text.toString()
            val newDescription = binding.etProfileDescription.text.toString()

            if (newName != userPage.name || newDescription != userPage.description) {
                updateProfile(newName, newDescription)
            } else {
                onBackPressed()
            }
        }
    }

    private fun updateProfile(newName: String, newDescription: String) {
        val updateProfile = userClient.updateProfile(
            userPage.id, UpdateProfile(newName, newDescription)
        )

        updateProfile.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() in 200..299) {
                    userPage.name = newName
                    userPage.description = newDescription

                    val intent = Intent()

                    intent.putExtra(
                        USER_NEW_PROFILE_KEY,
                        GsonUtils.INSTANCE.toJson(userPage)
                    )

                    setResult(RESULT_OK, intent)
                    finish()
                    return
                } else {
                    showSavingError()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                showSavingError()
            }
        })
    }

    private fun showSavingError() {
        Toast.makeText(applicationContext, R.string.saving_error, Toast.LENGTH_LONG).show()
        onBackPressed()
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
                .setImageResource(it.name.icon)
            talentCard.findViewById<TextView>(R.id.tv_talent_name).text = it.name.s

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
            val talentsToAdd = GsonUtils.INSTANCE.fromJson<List<RoleSimple>>(
                data?.getStringExtra(TALENTS_TO_ADD_KEY),
                object : TypeToken<List<RoleSimple>>() {}.type
            )

            val talentsToRemove = GsonUtils.INSTANCE.fromJson<List<RoleSimple>>(
                data?.getStringExtra(TALENTS_TO_REMOVE_KEY),
                object : TypeToken<List<RoleSimple>>() {}.type
            )

            userPage.roles.addAll(talentsToAdd)
            userPage.roles.removeAll(talentsToRemove)

            showTalentList()
        }
    }
}
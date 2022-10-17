package com.sounge.soungeapp.actitivy

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.sounge.soungeapp.R
import com.sounge.soungeapp.data.UserPage
import com.sounge.soungeapp.databinding.ActivityEditProfileBinding
import com.sounge.soungeapp.fragment.ProfileFragment.Constants.USER_PAGE_KEY
import com.sounge.soungeapp.utils.GsonUtils
import com.squareup.picasso.Picasso

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var userPage: UserPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        userPage = GsonUtils.INSTANCE.fromJson(
            intent.getStringExtra(USER_PAGE_KEY),
            UserPage::class.java
        )

        setCurrentInfo()
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
            // TODO: Salvar alterações no banco
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
        userPage.roles.forEachIndexed { i, it ->
            val talentCard = layoutInflater.inflate(R.layout.card_talent, null)
            talentCard.findViewById<ImageView>(R.id.iv_talent_icon)
                .setImageResource(it.roleName.icon)
            talentCard.findViewById<TextView>(R.id.tv_talent_name).text = it.roleName.s

            binding.llEditTalentList.addView(talentCard)

            if (userPage.roles.size != i + 1) {
                val space = Space(this)

                val layoutParams = LinearLayout.LayoutParams(
                    16,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                space.layoutParams = layoutParams

                binding.llEditTalentList.addView(space)
            }
        }
    }
}
package com.sounge.soungeapp.actitivy

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.sounge.soungeapp.R
import com.sounge.soungeapp.response.GenreSimple
import com.sounge.soungeapp.response.GroupPage
import com.sounge.soungeapp.response.UserSimple
import com.sounge.soungeapp.databinding.ActivityBandProfileBinding
import com.sounge.soungeapp.enums.GenreName
import com.sounge.soungeapp.utils.ImageUtils

class BandProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBandProfileBinding
    private lateinit var bandPage: GroupPage

    private lateinit var viewer: UserSimple

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBandProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewer = UserSimple(1, "Danielzinho do Rock", "", true)

        getBandInfo()

        hideViewsIfViewerNotOwner()

        setupActionBar()
        setListeners()
    }

    private fun hideViewsIfViewerNotOwner() {
        if (!bandPage.users.any { u -> u.id == viewer.id }) {
            binding.fabWritePost.visibility = View.GONE
            binding.btEditProfile.visibility = View.GONE
        }
    }

    private fun getBandInfo() {
        bandPage = mockBand()
    }

    private fun mockBand(): GroupPage {
        return GroupPage(
            1,
            "Tropa do Rock",
            "MKASKSA",
            2,
            arrayListOf(GenreSimple(1, GenreName.ROCK)),
            arrayListOf(UserSimple(2, "", "", true)),
            "",
            ""
        )
    }

    private fun setupActionBar() {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)

        supportActionBar!!.setCustomView(R.layout.action_bar_back)

        findViewById<ImageView>(R.id.iv_back_button).setOnClickListener {
            onBackPressed()
        }

        findViewById<TextView>(R.id.tv_page_name).text = bandPage.name
    }

    private fun setListeners() {
        binding.ivProfileBanner.setOnClickListener {
            ImageUtils.popupImage(
                binding.ivProfileBanner.drawable,
                binding.root
            )
        }

        binding.ivProfilePicture.setOnClickListener {
            ImageUtils.popupImage(
                binding.ivProfilePicture.drawable,
                binding.root
            )
        }

        binding.btEditProfile.setOnClickListener {
        }

        binding.fabWritePost.setOnClickListener {
        }
    }
}
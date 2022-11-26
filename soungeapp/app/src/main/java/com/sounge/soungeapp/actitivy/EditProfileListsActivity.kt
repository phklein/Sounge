package com.sounge.soungeapp.actitivy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.reflect.TypeToken
import com.sounge.soungeapp.R
import com.sounge.soungeapp.actitivy.EditProfileActivity.Constants.REQUEST_CODE_KEY
import com.sounge.soungeapp.actitivy.EditProfileActivity.Constants.UPDATE_GENRES_REQUEST_CODE
import com.sounge.soungeapp.actitivy.EditProfileActivity.Constants.UPDATE_TALENTS_REQUEST_CODE
import com.sounge.soungeapp.actitivy.EditProfileActivity.Constants.USER_LIST_KEY
import com.sounge.soungeapp.databinding.ActivityEditProfileListsBinding
import com.sounge.soungeapp.enums.GenreName
import com.sounge.soungeapp.enums.RoleName
import com.sounge.soungeapp.request.UpdateGenres
import com.sounge.soungeapp.request.UpdateRoles
import com.sounge.soungeapp.response.GenreSimple
import com.sounge.soungeapp.response.RoleSimple
import com.sounge.soungeapp.response.UserLogin
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils.Constants.USER_INFO_PREFS
import com.sounge.soungeapp.utils.SharedPreferencesUtils.Constants.USER_LOGIN_KEY
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileListsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileListsBinding

    private lateinit var viewer: UserLogin

    private var requestCode: Int = 0

    private lateinit var userTalentList: MutableList<RoleSimple>
    private lateinit var otherTalentList: MutableList<RoleSimple>
    private lateinit var userGenreList: MutableList<GenreSimple>
    private lateinit var otherGenreList: MutableList<GenreSimple>

    private lateinit var talentsToAdd: MutableList<RoleSimple>
    private lateinit var talentsToRemove: MutableList<RoleSimple>
    private lateinit var genresToAdd: MutableList<GenreSimple>
    private lateinit var genresToRemove: MutableList<GenreSimple>

    private lateinit var userClient: UserClient

    object Constants {
        const val TO_ADD_KEY = "toAdd"
        const val TO_REMOVE_KEY = "toRemove"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileListsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewer = GsonUtils.INSTANCE.fromJson(
            SharedPreferencesUtils.get(this, USER_INFO_PREFS, USER_LOGIN_KEY),
            UserLogin::class.java
        )

        userClient = Retrofit.getInstance().create(UserClient::class.java)

        requestCode = intent.getIntExtra(REQUEST_CODE_KEY, 0)


        when (requestCode) {
            UPDATE_TALENTS_REQUEST_CODE -> {
                binding.tvMyList.text = getString(R.string.my_talents)
                binding.tvOtherList.text = getString(R.string.other_talents)

                userTalentList = GsonUtils.INSTANCE.fromJson(
                    intent.getStringExtra(USER_LIST_KEY),
                    object : TypeToken<List<RoleSimple>>() {}.type
                )

                showUserList()

                otherTalentList = getOtherTalentList()

                showOtherList()

                talentsToAdd = ArrayList()
                talentsToRemove = ArrayList()

                setupActionBar()
            }
            UPDATE_GENRES_REQUEST_CODE -> {
                binding.tvMyList.text = getString(R.string.my_musical_interests)
                binding.tvOtherList.text = getString(R.string.other_musical_interests)

                userGenreList = GsonUtils.INSTANCE.fromJson(
                    intent.getStringExtra(USER_LIST_KEY),
                    object : TypeToken<List<GenreSimple>>() {}.type
                )

                showUserList()

                otherGenreList = getOtherGenreList()

                showOtherList()

                genresToAdd = ArrayList()
                genresToRemove = ArrayList()

                setupActionBar()
            }
            else -> {
                showError(getString(R.string.loading_error))
                onBackPressed()
            }
        }
    }

    private fun setupActionBar() {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar_edit)

        findViewById<TextView>(R.id.tv_action_cancel).setOnClickListener {
            onBackPressed()
        }

        findViewById<TextView>(R.id.tv_action_save).setOnClickListener {
            when (requestCode) {
                UPDATE_TALENTS_REQUEST_CODE -> {
                    val updateTalents = userClient.updateTalents(
                        viewer.id, UpdateRoles(
                            talentsToAdd.map { it.roleName }, talentsToRemove.map { it.roleName }
                        )
                    )

                    updateTalents.enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.code() in 200..299) {
                                val intent = Intent()

                                intent.putExtra(
                                    Constants.TO_ADD_KEY,
                                    GsonUtils.INSTANCE.toJson(talentsToAdd)
                                )

                                intent.putExtra(
                                    Constants.TO_REMOVE_KEY,
                                    GsonUtils.INSTANCE.toJson(talentsToRemove)
                                )

                                setResult(RESULT_OK, intent)
                                finish()
                            } else {
                                showError(getString(R.string.saving_error))
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            showError(getString(R.string.saving_error))
                        }

                    })
                }
                else -> {
                    val updateGenres = userClient.updateGenres(
                        viewer.id, UpdateGenres(
                            genresToAdd.map { it.genreName }, genresToRemove.map { it.genreName }
                        )
                    )

                    updateGenres.enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.code() in 200..299) {
                                val intent = Intent()

                                intent.putExtra(
                                    Constants.TO_ADD_KEY,
                                    GsonUtils.INSTANCE.toJson(genresToAdd)
                                )

                                intent.putExtra(
                                    Constants.TO_REMOVE_KEY,
                                    GsonUtils.INSTANCE.toJson(genresToRemove)
                                )

                                setResult(RESULT_OK, intent)
                                finish()
                            } else {
                                showError(getString(R.string.saving_error))
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            showError(getString(R.string.saving_error))
                        }

                    })
                }
            }
        }
    }

    private fun showError(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    private fun showUserList() {
        when (requestCode) {
            UPDATE_TALENTS_REQUEST_CODE -> {
                userTalentList.forEach {
                    val card = layoutInflater.inflate(R.layout.card, null)
                    card.findViewById<ImageView>(R.id.iv_card_icon)
                        .setImageResource(it.roleName.icon)
                    card.findViewById<TextView>(R.id.tv_card_name).text = it.roleName.s

                    val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    layoutParams.setMargins(24, 24, 24, 24)
                    card.layoutParams = layoutParams
                    card.tag = R.drawable.bg_purple_round_fill;

                    card.setOnClickListener { v ->
                        changeCardBackground(v)

                        if (talentsToRemove.contains(it)) {
                            talentsToRemove.remove(it)
                        } else {
                            talentsToRemove.add(it)
                        }
                    }

                    binding.fblMyList.addView(card)
                }
            }
            else -> {
                userGenreList.forEach {
                    val card = layoutInflater.inflate(R.layout.card, null)
                    card.findViewById<ImageView>(R.id.iv_card_icon).visibility = View.GONE
                    card.findViewById<TextView>(R.id.tv_card_name).text = it.genreName.s

                    val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    layoutParams.setMargins(24, 24, 24, 24)
                    card.layoutParams = layoutParams
                    card.tag = R.drawable.bg_purple_round_fill;

                    card.setOnClickListener { v ->
                        changeCardBackground(v)

                        if (genresToRemove.contains(it)) {
                            genresToRemove.remove(it)
                        } else {
                            genresToRemove.add(it)
                        }
                    }

                    binding.fblMyList.addView(card)
                }
            }
        }
    }

    private fun changeCardBackground(v: View) {
        when (v.tag) {
            R.drawable.bg_purple_round_fill -> {
                v.tag = R.drawable.bg_purple_round_outline;
                v.setBackgroundResource(R.drawable.bg_purple_round_outline)
            }
            else -> {
                v.tag = R.drawable.bg_purple_round_fill;
                v.setBackgroundResource(R.drawable.bg_purple_round_fill)
            }
        }
    }

    private fun getOtherTalentList(): MutableList<RoleSimple> {
        val list = ArrayList<RoleSimple>()

        RoleName.values().forEach {
            if (!userTalentList.any { t -> t.roleName == it }) {
                list.add(RoleSimple(1, it))
            }
        }

        return list
    }

    private fun getOtherGenreList(): MutableList<GenreSimple> {
        val list = ArrayList<GenreSimple>()

        GenreName.values().forEach {
            if (!userGenreList.any { t -> t.genreName == it }) {
                list.add(GenreSimple(1, it))
            }
        }

        return list
    }

    private fun showOtherList() {
        when (requestCode) {
            UPDATE_TALENTS_REQUEST_CODE -> {
                otherTalentList.forEach {
                    val card = layoutInflater.inflate(R.layout.card, null)
                    card.findViewById<ImageView>(R.id.iv_card_icon)
                        .setImageResource(it.roleName.icon)
                    card.findViewById<TextView>(R.id.tv_card_name).text = it.roleName.s

                    val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    layoutParams.setMargins(24, 24, 24, 24)
                    card.layoutParams = layoutParams
                    card.setBackgroundResource(R.drawable.bg_purple_round_outline)
                    card.tag = R.drawable.bg_purple_round_outline;

                    card.setOnClickListener { v ->
                        changeCardBackground(v)

                        if (talentsToAdd.contains(it)) {
                            talentsToAdd.remove(it)
                        } else {
                            talentsToAdd.add(it)
                        }
                    }

                    binding.fblOtherList.addView(card)
                }
            }
            else -> {
                otherGenreList.forEach {
                    val card = layoutInflater.inflate(R.layout.card, null)
                    card.findViewById<ImageView>(R.id.iv_card_icon).visibility = View.GONE
                    card.findViewById<TextView>(R.id.tv_card_name).text = it.genreName.s

                    val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    layoutParams.setMargins(24, 24, 24, 24)
                    card.layoutParams = layoutParams
                    card.setBackgroundResource(R.drawable.bg_purple_round_outline)
                    card.tag = R.drawable.bg_purple_round_outline;

                    card.setOnClickListener { v ->
                        changeCardBackground(v)

                        if (genresToAdd.contains(it)) {
                            genresToAdd.remove(it)
                        } else {
                            genresToAdd.add(it)
                        }
                    }

                    binding.fblOtherList.addView(card)
                }
            }
        }
    }
}
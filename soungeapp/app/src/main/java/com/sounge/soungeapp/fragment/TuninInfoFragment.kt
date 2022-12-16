package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sounge.soungeapp.databinding.FragmentTuninInfoBinding
import com.sounge.soungeapp.response.GroupMatch
import com.sounge.soungeapp.response.UserContact
import com.sounge.soungeapp.response.UserMatch
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response


class TuninInfoFragment(
    private val userMatch: UserMatch?,
    private val groupMatch: GroupMatch?,
    private val userContactId: Long?,
    private val ocult: Boolean
) : Fragment() {

    private lateinit var binding: FragmentTuninInfoBinding

    private lateinit var userClient: UserClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTuninInfoBinding.inflate(inflater, container, false)
        userClient = Retrofit.getInstance().create(UserClient::class.java)

        setInfoUser()

        return binding.root
    }

    private fun setInfoUser() {
        if (userMatch != null && groupMatch == null) {
            setContactDetail(userMatch, "user")
        } else
        if (groupMatch != null && userMatch == null) {
            setContactDetail(groupMatch, "group")
        } else {
            userClient.findContactDetails(userContactId!!).enqueue(
                object : retrofit2.Callback<UserMatch> {
                    override fun onResponse(
                        call: Call<UserMatch>,
                        response: Response<UserMatch>
                    ) {
                        if (response.code() == 200) {
                            setContactDetail(response.body()!!, "user")
                        }
                    }

                    override fun onFailure(
                        call: Call<UserMatch>,
                        t: Throwable
                    ) {
                        Toast.makeText(
                            requireActivity(),
                            "Erro inesperado...",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
        }
    }

    private fun setContactDetail(dataObject: Any?, type: String) {
        if (type == "user") {
            val userContactDetail = dataObject as UserMatch

            binding.tvName.text = userContactDetail.name
            binding.tvAge.text = userContactDetail.age.toString()
            binding.tvDescription.text = userContactDetail.description

            if (URLUtil.isValidUrl(userContactDetail.profilePic)) {
                Picasso.get().load(userContactDetail.profilePic).into(binding.ivProfilePic)
            }

            if (ocult) {
                binding.tvPhone.visibility = View.INVISIBLE
                binding.ivFirstDots.visibility = View.VISIBLE
                binding.ivSecondDots.visibility = View.VISIBLE
            } else {
                binding.tvPhone.text = userContactDetail.phone
                binding.ivFirstDots.visibility = View.INVISIBLE
                binding.ivSecondDots.visibility = View.INVISIBLE
            }
        } else {
            val groupContactDetail = dataObject as GroupMatch

            binding.tvName.text = groupContactDetail.name
            binding.tvAge.text = groupContactDetail.age.toString()
            binding.tvDescription.text = groupContactDetail.description

            if (URLUtil.isValidUrl(groupContactDetail.profilePic)) {
                Picasso.get().load(groupContactDetail.profilePic).into(binding.ivProfilePic)
            }

            binding.ivFirstDots.visibility = View.VISIBLE
            binding.ivSecondDots.visibility = View.VISIBLE
            binding.tvPhone.visibility = View.INVISIBLE
        }
    }
}
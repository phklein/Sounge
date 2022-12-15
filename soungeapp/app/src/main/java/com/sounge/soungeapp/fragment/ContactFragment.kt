package com.sounge.soungeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.sounge.soungeapp.R
import com.sounge.soungeapp.adapter.ContactAdapter
import com.sounge.soungeapp.databinding.FragmentContactsBinding
import com.sounge.soungeapp.response.Page
import com.sounge.soungeapp.response.UserContact
import com.sounge.soungeapp.response.UserLogin
import com.sounge.soungeapp.response.UserMatch
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.sounge.soungeapp.utils.GsonUtils
import com.sounge.soungeapp.utils.SharedPreferencesUtils
import retrofit2.Call
import retrofit2.Response


class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactsBinding

    private lateinit var userLogin: UserLogin
    private lateinit var contactList: MutableList<UserContact>

    private lateinit var userClient: UserClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        userClient = Retrofit.getInstance().create(UserClient::class.java)

        userLogin = GsonUtils.INSTANCE.fromJson(
            SharedPreferencesUtils.get(requireActivity(),
                SharedPreferencesUtils.Constants.USER_INFO_PREFS,
                SharedPreferencesUtils.Constants.USER_LOGIN_KEY
            ),
            UserLogin::class.java
        )

        findContactList()

        return binding.root
    }

    private fun findContactList() {
        userClient.findContactList(userLogin.id, 0).enqueue(
            object : retrofit2.Callback<MutableList<UserContact>> {
                override fun onResponse(
                    call: Call<MutableList<UserContact>>,
                    response: Response<MutableList<UserContact>>
                ) {
                    when (response.code()) {
                        200 ->  {
                            contactList = response.body()!!
                            setupRecyclerView()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<MutableList<UserContact>>,
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

    private fun setupRecyclerView() {
        val recyclerContainer = binding.recyclerContainer

        recyclerContainer.layoutManager = LinearLayoutManager(requireActivity())
        recyclerContainer.adapter = ContactAdapter(contactList) { contact ->
            replaceFragment(TuninInfoFragment(null, contact.id, false))
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val currentFragment = requireFragmentManager().findFragmentByTag(fragment.javaClass.name)
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

        if (currentFragment != null && currentFragment.isVisible) {
            return
        }

        transaction.replace(R.id.fl_main, fragment, fragment.javaClass.name)
        transaction.commitAllowingStateLoss()
    }

    private fun mockContactList(): List<UserContact> {
        return listOf(
            UserContact(
                27,
                "Aleff",
                "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=ting&dpr=2&h=650&w=940",
                true,
                "+55 (11) 91234-5678"
            ),
            UserContact(
                28,
                "Daniel",
                "https://veja.abril.com.br/wp-content/uploads/2016/06/alx_maria_bethania3_original.jpeg",
                true,
                "+55 (11) 91234-5678"
            )
        )
    }

}
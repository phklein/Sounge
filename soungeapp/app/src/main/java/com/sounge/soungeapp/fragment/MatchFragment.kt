package com.sounge.soungeapp.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sounge.soungeapp.R
import com.sounge.soungeapp.adapter.SwipeAdapter
import com.sounge.soungeapp.databinding.FragmentMatchBinding
import com.sounge.soungeapp.rest.Retrofit
import com.sounge.soungeapp.rest.UserClient
import com.yalantis.library.Koloda


class MatchFragment : Fragment() {

    private lateinit var binding: FragmentMatchBinding

    private var swipeAdapter: SwipeAdapter? = null
    private var list: ArrayList<Int>? = null
    lateinit var koloda: Koloda

    private lateinit var userClient: UserClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchBinding.inflate(inflater, container, false)
        userClient = Retrofit.getInstance().create(UserClient::class.java)

        koloda = binding.root.findViewById(R.id.koloda)

        list = ArrayList()

        swipeAdapter = SwipeAdapter(binding.root.context, listOf(1,2,3,4,5))
        koloda.adapter = swipeAdapter

//        getMatchInfo()

//        setListeners()

        return binding.root
    }

    private fun getMatchInfo() {
//        userPage = mockProfile()

//        setupRecyclerView()
//        setProfileInfo()
    }

}
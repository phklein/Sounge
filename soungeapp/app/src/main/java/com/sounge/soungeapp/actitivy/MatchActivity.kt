package com.sounge.soungeapp.actitivy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.sounge.soungeapp.R
import com.sounge.soungeapp.adapter.ViewPagerAdapter
import com.sounge.soungeapp.fragment.MatchFragment
import com.sounge.soungeapp.fragment.MatchFragment2

class MatchActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        viewPager = findViewById(R.id.viewPager)

        val fragments: ArrayList<Fragment> = arrayListOf(
            MatchFragment(),
            MatchFragment2()
        )

        adapter = ViewPagerAdapter(fragments, this)
        viewPager.adapter = adapter
    }

    override fun onBackPressed() {
        if(viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }
}
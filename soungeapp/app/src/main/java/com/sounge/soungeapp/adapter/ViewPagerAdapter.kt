package com.sounge.soungeapp.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sounge.soungeapp.fragment.MatchFragment

class ViewPagerAdapter(
    val items: ArrayList<MatchFragment>,
    activity: AppCompatActivity
    ): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): MatchFragment {
        return items[position]
    }

    fun getItem(position: Int) : MatchFragment {
        return items[position]
    }
}
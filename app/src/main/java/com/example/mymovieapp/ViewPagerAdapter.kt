package com.example.mymovieapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val fragList:List<Fragment>):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
       return fragList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragList[position]
    }
}
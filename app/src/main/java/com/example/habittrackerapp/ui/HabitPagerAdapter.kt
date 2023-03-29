package com.example.habittrackerapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habittrackerapp.data.HabitType
import com.example.habittrackerapp.ui.habitList.HabitListFragment

class HabitPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> HabitListFragment.newInstance(HabitType.GOOD)
            else -> HabitListFragment.newInstance(HabitType.BAD)
        }
}
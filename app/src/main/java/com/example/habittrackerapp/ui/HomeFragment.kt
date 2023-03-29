package com.example.habittrackerapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data.HabitType
import com.example.habittrackerapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var habitPagerAdapter: HabitPagerAdapter

    private val tabs = HabitType.values().map { habitType -> habitType.value }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        setPagerAdapter()
        setTabLayout()
        setOnClickListener()
        return binding.root
    }

    private fun setOnClickListener() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_CreateHabitFragment)
        }
    }

    private fun setPagerAdapter() {
        habitPagerAdapter = HabitPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = habitPagerAdapter

    }

    private fun setTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }
}
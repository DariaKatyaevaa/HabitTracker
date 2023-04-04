package com.example.habittrackerapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data.HabitType
import com.example.habittrackerapp.databinding.FragmentHomeBinding
import com.example.habittrackerapp.vm.HabitListViewModel
import com.example.habittrackerapp.vm.SortType
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var habitPagerAdapter: HabitPagerAdapter
    private lateinit var viewModel: HabitListViewModel

    private val tabs = HabitType.values().map { habitType -> habitType.value }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[HabitListViewModel::class.java]
        setPagerAdapter()
        setTabLayout()
        setOnClickListener()
        setFilterListeners()
        return binding.root
    }

    private fun setOnClickListener() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_CreateHabitFragment)
        }
    }

    private fun setFilterListeners() {
        binding.filters.priorityFilterDownArrow.setOnClickListener {
            viewModel.setPrioritySortByAscending()
        }
        binding.filters.priorityFilterUpArrow.setOnClickListener {
            viewModel.setPrioritySortByDescending()
        }
        binding.filters.dateFilterDownArrow.setOnClickListener {
            viewModel.setDateSortByAscending()
        }
        binding.filters.dateFilterUpArrow.setOnClickListener {
            viewModel.setDateSortByDescending()
        }
        binding.filters.buttonResetFilters.setOnClickListener {
            binding.filters.searchHabitTextField.setText("")
            viewModel.resetFilters()
        }
        binding.filters.searchHabitTextField.doAfterTextChanged {
            viewModel.setFilterByName(it.toString())
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
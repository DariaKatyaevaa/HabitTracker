package com.example.habittrackerapp.ui.habitList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data.HabitType
import com.example.habittrackerapp.databinding.FragmentHabitListBinding
import com.example.habittrackerapp.ui.habitCreate.CreateHabitFragment
import com.example.habittrackerapp.vm.HabitListViewModel
import java.util.UUID

class HabitListFragment : Fragment() {

    private lateinit var binding: FragmentHabitListBinding
    private lateinit var habitListAdapter: HabitListAdapter
    private lateinit var habitListViewModel: HabitListViewModel
    private lateinit var habitRecyclerView: RecyclerView
    private lateinit var habitType: HabitType

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitListBinding.inflate(inflater)
        habitListViewModel =
            ViewModelProviders.of(requireActivity()).get(HabitListViewModel::class.java)
        arguments?.let {
            it.getParcelable<HabitType>(ARG_HABIT_TYPE)?.let { type ->
                habitType = type
                habitListViewModel.habitType = type
            }
        }
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        habitListAdapter = HabitListAdapter(this)
        val habitListLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        habitRecyclerView = binding.habitList
        habitRecyclerView.apply {
            adapter = habitListAdapter
            layoutManager = habitListLayoutManager

        }
        habitListAdapter.submitList(habitListViewModel.getFilteredList())
        habitListViewModel.habitList.observe(viewLifecycleOwner) {
            habitListViewModel.habitType = habitType
            habitListAdapter.submitList(habitListViewModel.getFilteredList())
        }
        habitListViewModel.filters.observe(viewLifecycleOwner) {
            habitListViewModel.habitType = habitType
            habitListAdapter.submitList(habitListViewModel.getFilteredList())
        }
    }

    fun editHabit(habitId: UUID) {
        findNavController().navigate(
            R.id.action_HomeFragment_to_CreateHabitFragment,
            bundleOf(CreateHabitFragment.ARG_HABIT_ID to habitId)
        )
    }

    companion object {
        private const val ARG_HABIT_TYPE = "habit type"

        fun newInstance(habitType: HabitType) =
            HabitListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_HABIT_TYPE, habitType)
                }
            }
    }
}
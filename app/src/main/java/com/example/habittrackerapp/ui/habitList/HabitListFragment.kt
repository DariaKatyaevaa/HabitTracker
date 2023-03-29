package com.example.habittrackerapp.ui.habitList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.HabitTracker
import com.example.habittrackerapp.HabitTrackerController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data.HabitType
import com.example.habittrackerapp.databinding.FragmentHabitListBinding
import com.example.habittrackerapp.ui.habitCreate.CreateHabitFragment

class HabitListFragment : Fragment() {

    private lateinit var binding: FragmentHabitListBinding
    private lateinit var habitListAdapter: HabitListAdapter
    private lateinit var controller: HabitTrackerController
    private lateinit var habitRecyclerView: RecyclerView
    private lateinit var habitType: HabitType

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitListBinding.inflate(inflater)
        controller = HabitTracker.applicationContext().controller
        arguments?.let {
            it.getParcelable<HabitType>(ARG_HABIT_TYPE)?.let { type ->
                habitType = type
                controller.habitType = type
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
        habitListAdapter.submitList(controller.filteredByType())
        controller.habitList.observe(viewLifecycleOwner) {
            controller.habitType = habitType
            habitListAdapter.submitList(controller.filteredByType())
        }
    }

    fun editHabit(habitName: String) {
        findNavController().navigate(
            R.id.action_HomeFragment_to_CreateHabitFragment,
            bundleOf(CreateHabitFragment.ARG_HABIT_NAME to habitName)
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
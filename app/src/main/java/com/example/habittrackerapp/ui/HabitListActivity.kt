package com.example.habittrackerapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.HabitTracker
import com.example.habittrackerapp.HabitTrackerController
import com.example.habittrackerapp.databinding.ActivityMainBinding


class HabitListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var habitListAdapter: HabitListAdapter
    private lateinit var controller: HabitTrackerController
    private lateinit var habitRecyclerView: RecyclerView

    private val onButtonClickListener = View.OnClickListener {
        Intent(this, CreateHabitActivity::class.java)
            .run { startActivity(this) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = HabitTracker.applicationContext().controller
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setRecyclerView()

        binding.fab.setOnClickListener(onButtonClickListener)
    }

    private fun setRecyclerView() {
        habitListAdapter = HabitListAdapter(controller, this)
        val habitListLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        habitRecyclerView = binding.habitList
        habitRecyclerView.apply {
            adapter = habitListAdapter
            layoutManager = habitListLayoutManager
        }
        habitListAdapter.submitList(controller.habitList.value)
        controller.habitList.observe(this) { list -> habitListAdapter.submitList(list.toMutableList()) }
    }

    fun editHabit(habitName: String) {
        val habit = controller.getHabitByName(habitName)!!
        controller.removeHabit(habit)
        val intent = Intent(this, CreateHabitActivity::class.java)
        intent.putExtra(HabitKey, habit)
        startActivity(intent)
    }
}
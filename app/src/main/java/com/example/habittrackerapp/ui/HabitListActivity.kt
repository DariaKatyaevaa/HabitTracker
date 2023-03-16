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
    private lateinit var adapter: HabitListRecycleAdapter
    private lateinit var controller: HabitTrackerController

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
        val habitList: RecyclerView = binding.habitList
        adapter = HabitListRecycleAdapter(controller, this)
        habitList.adapter = adapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        habitList.layoutManager = layoutManager
    }

    fun editHabit(habitName: String) {
        val habit = controller.getHabitByName(habitName)!!
        controller.removeHabit(habit)
        val intent: Intent = Intent(this, CreateHabitActivity::class.java)
        intent.putExtra("habit", habit)
        startActivity(intent)
    }
}
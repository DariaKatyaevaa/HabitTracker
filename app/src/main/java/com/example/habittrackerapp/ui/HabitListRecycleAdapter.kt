package com.example.habittrackerapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.HabitTrackerController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data.Habit

class HabitListRecycleAdapter(
    private val controller: HabitTrackerController,
    private val habitListActivity: HabitListActivity
) :
    RecyclerView.Adapter<HabitListRecycleAdapter.HabitViewHolder>() {

    private var habits: LiveData<MutableList<Habit>> = controller.habitList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_list_item, parent, false)
        return HabitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit: Habit = controller.habitList.value!![position]

        with(holder) {
            nameTextView.text = habit.name
            descriptionTextView.text = habit.description
            colorView.setBackgroundColor(
                ContextCompat.getColor(
                    habitListActivity,
                    habit.color.colorCode
                )
            )
            val info = "priority: ${habit.priority} " +
                    "type:${habit.type} " +
                    "count:${habit.executionCount} " +
                    "period:${habit.periodicity}"
            infoTextView.text = info
        }

        holder.itemView.setOnClickListener {
            habitListActivity.editHabit(habit.name)
        }

        println("DASHA")
        println(holder)
    }

    override fun getItemCount() = habits.value!!.size

    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.habitItemName)
        val descriptionTextView: TextView = itemView.findViewById(R.id.habitItemDescription)
        val infoTextView: TextView = itemView.findViewById(R.id.habitItemInfo)
        val colorView: View = itemView.findViewById(R.id.habitItemColor)
    }
}

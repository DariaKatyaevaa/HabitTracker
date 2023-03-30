package com.example.habittrackerapp.ui.habitList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data.Habit

class HabitListAdapter(
    private val habitListFragment: HabitListFragment
) : ListAdapter<Habit, HabitListAdapter.HabitViewHolder>(HabitDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): HabitViewHolder =
        HabitViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.habit_list_item, parent, false)
        )


    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView by lazy { itemView.findViewById(R.id.habitItemName) }
        private val descriptionTextView: TextView by lazy { itemView.findViewById(R.id.habitItemDescription) }
        private val infoTextView: TextView by lazy { itemView.findViewById(R.id.habitItemInfo) }
        private val colorView: View by lazy { itemView.findViewById(R.id.habitItemColor) }

        fun bind(habit: Habit) {
            itemView.setOnClickListener {
                habitListFragment.editHabit(habit.name)
            }

            nameTextView.text = habit.name
            descriptionTextView.text = habit.description
            colorView.setBackgroundColor(
                ContextCompat.getColor(
                    habitListFragment.requireContext(),
                    habit.color.colorCode
                )
            )
            infoTextView.text = String.format(
                habitListFragment.resources.getString(R.string.habitInfo),
                habit.priority.stringName,
                habit.type,
                habit.executionCount,
                habit.periodicity
            )
        }

    }

    class HabitDiffCallback : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit) = oldItem == newItem
    }
}
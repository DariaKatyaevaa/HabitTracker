package com.example.habittrackerapp.vm

import androidx.lifecycle.ViewModel
import com.example.habittrackerapp.HabitTracker
import com.example.habittrackerapp.data.ColorType
import com.example.habittrackerapp.data.Habit
import com.example.habittrackerapp.data.HabitPriority

class CreateHabitViewModel : ViewModel() {
    private val controller = HabitTracker.applicationContext().controller

    fun addHabit(
        name: String,
        description: String,
        priority: String,
        habitType: String,
        count: Int,
        period: Int,
        color: ColorType
    ) {
        val habitPriority = HabitPriority.values().find { p -> p.stringName == priority }!!
        val habit = Habit(name, description, habitPriority, habitType, count, period, color)
        controller.addHabit(habit)
    }

    fun editHabit(
        oldHabit: Habit,
        name: String,
        description: String,
        priority: String,
        habitType: String,
        count: Int,
        period: Int,
        color: ColorType
    ) {
        controller.removeHabit(oldHabit)
        addHabit(name, description, priority, habitType, count, period, color)
    }

    fun findHabitByName(name: String): Habit? {
        return controller.getHabitByName(name)
    }
}
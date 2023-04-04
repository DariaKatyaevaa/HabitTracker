package com.example.habittrackerapp.vm

import androidx.lifecycle.ViewModel
import com.example.habittrackerapp.HabitTracker
import com.example.habittrackerapp.data.ColorType
import com.example.habittrackerapp.data.Habit
import com.example.habittrackerapp.data.HabitPriority
import java.util.UUID

class CreateHabitViewModel : ViewModel() {
    private val repository = HabitTracker.applicationContext().repository

    fun addHabit(
        name: String,
        description: String,
        priority: String,
        habitType: String,
        count: Int,
        period: Int,
        color: ColorType
    ) {
        HabitPriority.values().find { p -> p.stringName == priority }?.let { habitPriority ->
            val habit = Habit(
                name,
                description,
                habitPriority,
                habitType,
                count,
                period,
                color.colorCode
            )
            repository.createHabit(habit)
        }
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
        repository.removeHabit(oldHabit)
        addHabit(name, description, priority, habitType, count, period, color)
    }

    fun findHabitById(id: UUID): Habit? {
        return repository.getHabitById(id)
    }
}
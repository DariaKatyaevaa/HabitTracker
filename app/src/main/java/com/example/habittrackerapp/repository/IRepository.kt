package com.example.habittrackerapp.repository

import androidx.lifecycle.LiveData
import com.example.habittrackerapp.data.Habit
import java.util.UUID

interface IRepository {
    fun getAllHabit(): LiveData<List<Habit>>

    fun getHabitById(id: UUID): Habit?

    fun createHabit(habit: Habit)

    fun editHabit(habit: Habit)

    fun removeHabit(habit: Habit)
}
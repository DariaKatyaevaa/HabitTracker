package com.example.habittrackerapp.repository

import androidx.lifecycle.LiveData
import com.example.habittrackerapp.data.Habit
import java.util.UUID

interface IRepository {
    fun getAllHabit(): LiveData<List<Habit>>

    fun getHabitById(id: UUID): Habit?

    suspend fun createHabit(habit: Habit)

    suspend fun editHabit(habit: Habit)

    suspend fun removeHabit(habit: Habit)
}
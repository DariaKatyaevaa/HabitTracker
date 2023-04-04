package com.example.habittrackerapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.habittrackerapp.data.Habit
import java.util.UUID

class HabitRepository(context: Context) : IRepository {
    private val habitDao = AppDatabase.getInstance(context).habitDao()

    override fun getAllHabit(): LiveData<List<Habit>> = habitDao.getAll()

    override fun getHabitById(id: UUID): Habit? = habitDao.getHabitById(id)

    override fun createHabit(habit: Habit) = habitDao.createHabit(habit)

    override fun editHabit(habit: Habit) = habitDao.editHabit(habit)

    override fun removeHabit(habit: Habit) = habitDao.removeHabit(habit)
}
package com.example.habittrackerapp.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habittrackerapp.data.Habit
import java.util.UUID

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAll(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitById(habitId: UUID): Habit?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createHabit(habit: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun editHabit(habit: Habit)

    @Delete
    fun removeHabit(habit: Habit)
}
package com.example.habittrackerapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habittrackerapp.data.ColorType
import com.example.habittrackerapp.data.Habit

class HabitTrackerController {
    private val habits = MutableLiveData(mutableListOf<Habit>())
    val habitList: LiveData<MutableList<Habit>>
        get() = habits

    init {
        test()
    }

    fun addHabit(habit: Habit) {
        habits.value!!.add(habit)
    }

    fun removeHabit(habit: Habit) {
        habits.value!!.remove(habit)
    }

    fun getHabitByName(nameHabit: String): Habit? {
        habits.value!!.forEach {
            if (it.name == nameHabit) {
                return it
            }
        }
        return null
    }

    private fun test() {
        habits.value!!.add(
            Habit(
                "Read book",
                "Dragon book",
                "middle",
                "good",
                2,
                1,
                ColorType.Blue
            )
        )

        habits.value!!.add(
            Habit(
                "drink cola",
                "1 cup",
                "low",
                "bad",
                1,
                3,
                ColorType.Pink
            )
        )
    }
}
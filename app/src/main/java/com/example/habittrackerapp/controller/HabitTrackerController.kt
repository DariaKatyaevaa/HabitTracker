package com.example.habittrackerapp.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habittrackerapp.data.ColorType
import com.example.habittrackerapp.data.Habit
import com.example.habittrackerapp.data.HabitPriority
import com.example.habittrackerapp.data.HabitType

class HabitTrackerController {
    private val habits = MutableLiveData(mutableListOf<Habit>())
    val habitList: LiveData<MutableList<Habit>>
        get() = habits

    init {
        test()
    }

    fun addHabit(habit: Habit) {
        habits.value = habits.value!!.apply { add(habit) }
    }

    fun removeHabit(habit: Habit) {
        habits.value = habits.value!!.apply { remove(habit) }
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
                HabitPriority.MIDDLE,
                "good",
                2,
                1,
                ColorType.Blue
            )
        )

        habits.value!!.add(
            Habit(
                "2 Read book",
                "War and Peace",
                HabitPriority.HIGH,
                "good",
                5,
                1,
                ColorType.Green
            )
        )

        habits.value!!.add(
            Habit(
                "Drink water",
                "with lemon",
                HabitPriority.MIDDLE,
                "good",
                5,
                1,
                ColorType.Yellow
            )
        )

        habits.value!!.add(
            Habit(
                "drink cola",
                "1 cup",
                HabitPriority.LOW,
                "bad",
                1,
                3,
                ColorType.Pink
            )
        )

        habits.value!!.add(
            Habit(
                "eat chis",
                "description",
                HabitPriority.HIGH,
                "bad",
                1,
                1,
                ColorType.Yellow
            )
        )

        habits.value!!.add(
            Habit(
                "smoke",
                "description",
                HabitPriority.MIDDLE,
                "bad",
                2,
                3,
                ColorType.Purple
            )
        )
    }
}
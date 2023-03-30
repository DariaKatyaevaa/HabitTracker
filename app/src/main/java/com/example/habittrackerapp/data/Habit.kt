package com.example.habittrackerapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    var name: String,
    var description: String,
    var priority: HabitPriority,
    var type: String,
    var executionCount: Int,
    var periodicity: Int,
    var color: ColorType
) : Parcelable {}

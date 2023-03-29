package com.example.habittrackerapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class HabitType(val value: String) : Parcelable {
    GOOD("good"),
    BAD("bad")
}
package com.example.habittrackerapp.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
enum class HabitType(val value: String) : Parcelable {
    GOOD("good"),
    BAD("bad")
}
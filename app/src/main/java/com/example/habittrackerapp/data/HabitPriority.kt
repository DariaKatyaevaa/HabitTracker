package com.example.habittrackerapp.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
enum class HabitPriority(val value: Int, val stringName: String) : Parcelable {
    LOW(0, "low"),
    MIDDLE(1, "middle"),
    HIGH(2, "high")
}
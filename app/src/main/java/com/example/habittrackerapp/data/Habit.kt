package com.example.habittrackerapp.data

import android.os.Parcelable
import com.example.habittrackerapp.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    var name: String,
    var description: String,
    var priority: String,
    var type: String,
    var executionCount: Int,
    var periodicity: Int,
    var color: ColorType
) : Parcelable {}

enum class ColorType(val colorCode: Int) {
    Green(colorCode = R.color._green),
    Blue(colorCode = R.color._blue),
    Pink(colorCode = R.color._pink),
    Purple(colorCode = R.color._purple),
    Yellow(colorCode = R.color._yellow),
}
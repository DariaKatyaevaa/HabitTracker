package com.example.habittrackerapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "habits")
@Parcelize
data class Habit(
    val name: String,
    val description: String,
    val priority: HabitPriority,
    val type: String,
    val executionCount: Int,
    val periodicity: Int,
    val color: Int,
    @PrimaryKey val id: UUID = UUID.randomUUID(),
) : Parcelable {}

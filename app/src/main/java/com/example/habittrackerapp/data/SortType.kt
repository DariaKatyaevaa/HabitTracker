package com.example.habittrackerapp.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
enum class SortType : Parcelable {
    DateSortByAscending,
    DateSortByDescending,
    PrioritySortByAscending,
    PrioritySortByDescending,
    None
}
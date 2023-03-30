package com.example.habittrackerapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittrackerapp.HabitTracker
import com.example.habittrackerapp.controller.HabitTrackerController
import com.example.habittrackerapp.data.Habit
import com.example.habittrackerapp.data.HabitType

class HabitListViewModel : ViewModel() {
    private val controller: HabitTrackerController = HabitTracker.applicationContext().controller
    val habitList: LiveData<MutableList<Habit>> = controller.habitList
    private val _filters: MutableLiveData<Filters> = MutableLiveData(Filters())
    val filters: LiveData<Filters> get() = _filters
    var habitType = HabitType.GOOD

    fun getFilteredList(): MutableList<Habit> {
        var filteredList = habitList.value!!
        if (_filters.value!!.isDateSort) {
            if (_filters.value!!.sortType == SortType.Descending) {
                filteredList = filteredList.asReversed()
            }
        }
        if (_filters.value!!.isPrioritySort) {
            if (_filters.value!!.sortType == SortType.Descending) {
                filteredList =
                    filteredList.sortedByDescending { h -> h.priority.value }.toMutableList()
            } else {
                filteredList =
                    filteredList.sortedBy { h -> h.priority.value }.toMutableList()
            }
        }
        if (_filters.value!!.searchName != null) {
            filteredList =
                filteredList.filter { h ->
                    h.name.contains(
                        filters.value!!.searchName!!,
                        ignoreCase = true
                    )
                }.toMutableList()
        }
        return filteredList.filter { habit: Habit -> habit.type == habitType.value }
            .toMutableList()
    }

    fun setFilterByName(input: String) {
        val newFilters = _filters.value!!.apply {
            this.searchName = input
        }
        _filters.value = newFilters
    }

    fun setSortByPriority(sortType: SortType) {
        val newFilters = _filters.value!!.apply {
            this.sortType = sortType
            this.isPrioritySort = true
        }
        _filters.value = newFilters
    }

    fun setSortByDate(sortType: SortType) {
        val newFilters = _filters.value!!.apply {
            this.sortType = sortType
            this.isDateSort = true
        }
        _filters.value = newFilters
    }

    fun resetFilters() {
        _filters.value = Filters()
    }
}

enum class SortType {
    Ascending,
    Descending
}

class Filters(
    var sortType: SortType? = null,
    var isDateSort: Boolean = false,
    var isPrioritySort: Boolean = false,
    var searchName: String? = null
) {}
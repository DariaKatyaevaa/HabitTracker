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
        filters.value?.let { filters ->
            filters.sortType?.let { sortType ->
                filteredList = when (sortType) {
                    SortType.DateSortByDescending -> filteredList.asReversed()
                    SortType.DateSortByAscending -> filteredList
                    SortType.PrioritySortByDescending -> filteredList.sortedByDescending { h -> h.priority.value }
                        .toMutableList()
                    SortType.PrioritySortByAscending -> filteredList.sortedBy { h -> h.priority.value }
                        .toMutableList()
                }
            }
            filters.searchName?.let { name ->
                filteredList = filteredList.filter { h -> h.name.contains(name, ignoreCase = true) }
                    .toMutableList()
            }
        }
        return filteredList.filter { habit: Habit -> habit.type == habitType.value }
            .toMutableList()
    }

    fun setFilterByName(input: String) {
        val newFilters = _filters.value?.apply {
            this.searchName = input
        }
        _filters.value = newFilters
    }

    fun setPrioritySortByAscending() {
        val newFilters = _filters.value?.apply {
            this.sortType = SortType.PrioritySortByAscending
        }
        _filters.value = newFilters
    }

    fun setPrioritySortByDescending() {
        val newFilters = _filters.value?.apply {
            this.sortType = SortType.PrioritySortByDescending
        }
        _filters.value = newFilters
    }

    fun setDateSortByAscending() {
        val newFilters = _filters.value?.apply {
            this.sortType = SortType.DateSortByAscending
        }
        _filters.value = newFilters
    }

    fun setDateSortByDescending() {
        val newFilters = _filters.value?.apply {
            this.sortType = SortType.DateSortByDescending
        }
        _filters.value = newFilters
    }

    fun resetFilters() {
        _filters.value = Filters()
    }
}

enum class SortType {
    DateSortByAscending,
    DateSortByDescending,
    PrioritySortByAscending,
    PrioritySortByDescending
}

class Filters(
    var sortType: SortType? = null,
    var searchName: String? = null
) {}
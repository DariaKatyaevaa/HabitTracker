package com.example.habittrackerapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittrackerapp.HabitTracker
import com.example.habittrackerapp.data.Habit
import com.example.habittrackerapp.data.HabitType
import com.example.habittrackerapp.data.SortType
import com.example.habittrackerapp.repository.HabitRepository

class HabitListViewModel : ViewModel() {
    private val repository: HabitRepository = HabitTracker.applicationContext().repository
    val habitList: LiveData<List<Habit>> = repository.getAllHabit()
    private val _filters: MutableLiveData<Filters?> = MutableLiveData(Filters())
    val filters: MutableLiveData<Filters?> get() = _filters
    var habitType = HabitType.GOOD

    fun getFilteredList(): MutableList<Habit> {
        var filteredList = habitList.value ?: listOf<Habit>()
        filters.value?.let { filters ->
            filteredList = when (filters.sortType) {
                SortType.DateSortByDescending -> filteredList.asReversed()
                SortType.DateSortByAscending -> filteredList
                SortType.PrioritySortByDescending -> filteredList.sortedByDescending { h -> h.priority.value }
                    .toMutableList()
                SortType.PrioritySortByAscending -> filteredList.sortedBy { h -> h.priority.value }
                    .toMutableList()
                SortType.None -> filteredList
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

class Filters(
    var sortType: SortType = SortType.None,
    var searchName: String? = null
) {}
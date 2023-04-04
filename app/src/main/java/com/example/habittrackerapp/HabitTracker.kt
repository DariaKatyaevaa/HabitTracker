package com.example.habittrackerapp

import android.app.Application
import com.example.habittrackerapp.repository.HabitRepository

class HabitTracker : Application() {
    lateinit var repository: HabitRepository

    companion object {
        private var instance: HabitTracker? = null

        fun applicationContext(): HabitTracker {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        repository = HabitRepository(this)
    }
}
package com.example.habittrackerapp

import android.app.Application

class HabitTracker : Application() {
    val controller = HabitTrackerController()

    companion object {
        private var instance: HabitTracker? = null

        fun applicationContext(): HabitTracker {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
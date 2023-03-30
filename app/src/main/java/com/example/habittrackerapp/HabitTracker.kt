package com.example.habittrackerapp

import android.app.Application
import com.example.habittrackerapp.controller.HabitTrackerController

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
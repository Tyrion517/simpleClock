package com.example.simpleclock

import android.app.Application

class SimpleClockApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        RecordRepository.initialize(this)
    }
}
package com.example.mock_shop

import android.app.Application
import timber.log.Timber

class LoggingApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
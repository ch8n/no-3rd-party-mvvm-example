package com.example.ch8n

import android.app.Application
import timber.log.Timber

class AirtelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
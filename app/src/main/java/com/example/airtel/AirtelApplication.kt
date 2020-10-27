package com.example.airtel

import android.app.Application
import timber.log.Timber

class AirtelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
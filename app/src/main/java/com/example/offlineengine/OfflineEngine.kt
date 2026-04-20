package com.example.offlineengine

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class OfflineEngine  : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
package com.example.offlineengine

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.unit.Constraints
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.offlineengine.data.worker.NewsRefreshWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class OfflineEngine : Application() {

    override fun onCreate() {
        super.onCreate()
        setupWorker()
    }

    private fun setupWorker() {
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "news_refresh_worker",
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<NewsRefreshWorker>(1, TimeUnit.HOURS).setConstraints(
                androidx.work.Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 15, TimeUnit.MINUTES).build()
        )
    }
}
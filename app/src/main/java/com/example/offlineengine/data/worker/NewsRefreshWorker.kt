package com.example.offlineengine.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import okhttp3.internal.connection.Exchange

class NewsRefreshWorker(
    appContext: Context,
    params: WorkerParameters,
//    private val newsRepository: NewsRepository
) : CoroutineWorker(
    appContext,
    params
) {

    override suspend fun doWork(): Result {
        return try {

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
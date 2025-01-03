package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.api.AsteroidApiFilter
import com.udacity.asteroidradar.data.repository.AsteroidRepository
import retrofit2.HttpException
import timber.log.Timber

class RefreshDataWorker(
    private val asteroidRepository: AsteroidRepository, appContext: Context, params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            asteroidRepository.refreshAsteroids(AsteroidApiFilter.SHOW_WEEK)
            Result.success()
        } catch (e: HttpException) {
            Timber.d(e.toString())
            Result.retry()
        }
    }

}

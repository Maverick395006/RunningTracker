package com.maverick.runningtracker.repositories

import com.maverick.runningtracker.db.Run
import com.maverick.runningtracker.db.RunDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDao: RunDAO
) {

    suspend fun insertRun(run:Run) = runDao.insertRun(run)

    suspend fun deleteRun(run:Run) = runDao.deleteRun(run)

    fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()

    fun getAllRunsSortedByTimeInMillis() = runDao.getAllRunsSortedByTimeInMillis()

    fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurned()

    fun getAllRunsSortedByAvgSpeed() = runDao.getAllRunsSortedByAvgSpeed()

    fun getAllRunsSortedByDistance() = runDao.getAllRunsSortedByDistance()

    fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()

    fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

    fun getTotalDistanceInMeters() = runDao.getTotalDistanceInMeters()

    fun getTotalAvgSpeedInKMH() = runDao.getTotalAvgSpeedInKMH()

}
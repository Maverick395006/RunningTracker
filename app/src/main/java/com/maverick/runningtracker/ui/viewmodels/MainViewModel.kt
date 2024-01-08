package com.maverick.runningtracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maverick.runningtracker.db.Run
import com.maverick.runningtracker.repositories.MainRepository
import com.maverick.runningtracker.util.SortType
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(val mainRepository: MainRepository) : ViewModel() {


    private val runsSortedByDate = mainRepository.getAllRunsSortedByDate()
    private val runsSortedByTimeInMillis = mainRepository.getAllRunsSortedByTimeInMillis()
    private val runsSortedByCaloriesBurned = mainRepository.getAllRunsSortedByCaloriesBurned()
    private val runsSortedByAvgSpeed = mainRepository.getAllRunsSortedByAvgSpeed()
    private val runsSortedByDistance = mainRepository.getAllRunsSortedByDistance()

    val runs = MediatorLiveData<List<Run>>()

    var sortType = SortType.DATE

    init {
        runs.addSource(runsSortedByDate) { listOfRuns ->
            if (sortType == SortType.DATE) {
                listOfRuns?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByTimeInMillis) { listOfRuns ->
            if (sortType == SortType.RUNNING_TIME) {
                listOfRuns?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByCaloriesBurned) { listOfRuns ->
            if (sortType == SortType.CALORIES_BURNED) {
                listOfRuns?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByAvgSpeed) { listOfRuns ->
            if (sortType == SortType.AVG_SPEED) {
                listOfRuns?.let { runs.value = it }
            }
        }
        runs.addSource(runsSortedByDistance) { listOfRuns ->
            if (sortType == SortType.DISTANCE) {
                listOfRuns?.let { runs.value = it }
            }
        }
    }

    fun sortRuns(sortType: SortType) = when (sortType) {
        SortType.DATE -> runsSortedByDate.value?.let { runs.value = it }
        SortType.RUNNING_TIME -> runsSortedByTimeInMillis.value?.let { runs.value = it }
        SortType.AVG_SPEED -> runsSortedByAvgSpeed.value?.let { runs.value = it }
        SortType.DISTANCE -> runsSortedByDistance.value?.let { runs.value = it }
        SortType.CALORIES_BURNED -> runsSortedByCaloriesBurned.value?.let { runs.value = it }
    }.also {
        this.sortType = sortType
    }

    fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }

}
package com.maverick.runningtracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.maverick.runningtracker.repositories.MainRepository

class MainViewModel @ViewModelInject constructor(val mainRepository: MainRepository) : ViewModel() {
}
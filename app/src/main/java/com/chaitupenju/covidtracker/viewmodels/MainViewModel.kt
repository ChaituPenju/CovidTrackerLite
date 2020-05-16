package com.chaitupenju.covidtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.chaitupenju.covidtracker.repos.MainRepository
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepo: MainRepository) : ViewModel() {
    fun getStateWiseResponse() = liveData(Dispatchers.IO) {
        emit(mainRepo.getStateWiseResponse())
    }

    fun getWorldWiseResponse() = liveData(Dispatchers.IO) {
        emit(mainRepo.getWorldWiseResponse())
    }
}
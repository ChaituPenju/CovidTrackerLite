package com.chaitupenju.covidtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.chaitupenju.covidtracker.repos.MainRepository
import kotlinx.coroutines.Dispatchers

class StateDistrictViewModel(private val mainRepo: MainRepository) : ViewModel() {
    fun getStateDistrictWiseResponse() = liveData(Dispatchers.IO) {
        emit(mainRepo.getStateDistrictWiseResponse())
    }
}
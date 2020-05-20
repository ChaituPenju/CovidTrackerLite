package com.chaitupenju.covidtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.chaitupenju.covidtracker.repos.MainRepository
import kotlinx.coroutines.Dispatchers

class CountryStateViewModel(private val mainRepo: MainRepository): ViewModel() {
    fun getCountryUSAStateWiseResponse() = liveData(Dispatchers.IO) {
        emit(mainRepo.getCountryUSAStateWiseResponse())
    }
}
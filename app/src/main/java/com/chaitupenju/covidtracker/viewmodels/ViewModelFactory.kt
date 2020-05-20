package com.chaitupenju.covidtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chaitupenju.covidtracker.network.RetrofitService
import com.chaitupenju.covidtracker.repos.MainRepository

class ViewModelFactory(private val apiHelper: RetrofitService, private val activity: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (activity) {
            "MainActivity" -> return MainViewModel(MainRepository(apiHelper)) as T
            "StateDistrictActivity" -> return StateDistrictViewModel(MainRepository(apiHelper)) as T
            "CountryStateActivity" -> return CountryStateViewModel(MainRepository(apiHelper)) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}
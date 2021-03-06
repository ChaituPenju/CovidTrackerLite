package com.chaitupenju.covidtracker.repos

import com.chaitupenju.covidtracker.helpers.Constants.WORLD_WISE_URL
import com.chaitupenju.covidtracker.helpers.Constants.COUNTRY_WISE_URL
import com.chaitupenju.covidtracker.helpers.Constants.COUNTRY_STATE_USA_URL
import com.chaitupenju.covidtracker.network.RetrofitService

class MainRepository(private val apiService: RetrofitService) {
    suspend fun getStateWiseResponse() = apiService.getStateWiseReport()
    suspend fun getStateDistrictWiseResponse() = apiService.getStateDistrictWiseReport()
    suspend fun getWorldWiseResponse() = apiService.getWorldWiseResponse(WORLD_WISE_URL)
    suspend fun getCountryWiseResponse() = apiService.getCountryWiseResponse(COUNTRY_WISE_URL)
    suspend fun getCountryUSAStateWiseResponse() = apiService.getCountryUSAStateWiseResponse(COUNTRY_STATE_USA_URL)
}
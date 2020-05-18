package com.chaitupenju.covidtracker.network

import com.chaitupenju.covidtracker.models.CountryWiseItem
import com.chaitupenju.covidtracker.models.StateDistrictwiseItem
import com.chaitupenju.covidtracker.models.StateWiseResponse
import com.chaitupenju.covidtracker.models.WorldWiseResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitService {
    @GET("data.json")
    suspend fun getStateWiseReport(): StateWiseResponse

    @GET("v2/state_district_wise.json")
    suspend fun getStateDistrictWiseReport(): List<StateDistrictwiseItem>

    @GET
    suspend fun getWorldWiseResponse(@Url url: String): WorldWiseResponse

    @GET
    suspend fun getCountryWiseResponse(@Url url: String): List<CountryWiseItem>
}
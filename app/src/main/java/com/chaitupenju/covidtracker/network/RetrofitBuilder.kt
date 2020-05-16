package com.chaitupenju.covidtracker.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val STATEWISE_URL = "https://api.covid19india.org/"
    private const val WORLDWISE_URL = "https://api.covid19api.com/"

    private val retrofitObj = Retrofit.Builder()
        .baseUrl(STATEWISE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: RetrofitService = retrofitObj.create(RetrofitService::class.java)
}
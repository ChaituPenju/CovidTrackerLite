package com.chaitupenju.covidtracker.models

import com.google.gson.annotations.SerializedName

data class CountryWiseItem(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("PR")
	val pR: String? = null,

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("totalConfirmedPerMillionPopulation")
	val totalConfirmedPerMillionPopulation: Int? = null,

	@field:SerializedName("totalRecovered")
	val totalRecovered: String? = null,

	@field:SerializedName("dailyDeaths")
	val dailyDeaths: Int? = null,

	@field:SerializedName("totalCritical")
	val totalCritical: Int? = null,

	@field:SerializedName("FR")
	val fR: String? = null,

	@field:SerializedName("totalConfirmed")
	val totalConfirmed: String? = null,

	@field:SerializedName("lastUpdated")
	val lastUpdated: String? = null,

	@field:SerializedName("countryCode")
	val countryCode: String? = null,

	@field:SerializedName("totalDeaths")
	val totalDeaths: String? = null,

	@field:SerializedName("totalDeathsPerMillionPopulation")
	val totalDeathsPerMillionPopulation: Int? = null,

	@field:SerializedName("dailyConfirmed")
	val dailyConfirmed: Int? = null,

	@field:SerializedName("lat")
	val lat: Double? = null,

	@field:SerializedName("activeCases")
	val activeCases: String? = null
)

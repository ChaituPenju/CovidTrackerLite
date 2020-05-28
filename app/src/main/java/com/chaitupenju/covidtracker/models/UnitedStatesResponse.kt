package com.chaitupenju.covidtracker.models

import com.google.gson.annotations.SerializedName

data class UnitedStateItem(

	@field:SerializedName("cases")
	val cases: String? = null,

	@field:SerializedName("tests")
	val tests: Int? = null,

	@field:SerializedName("deathsPerOneMillion")
	val deathsPerOneMillion: Int? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("casesPerOneMillion")
	val casesPerOneMillion: Int? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("updated")
	val updated: Long? = null,

	@field:SerializedName("deaths")
	val deaths: String? = null,

	@field:SerializedName("testsPerOneMillion")
	val testsPerOneMillion: Int? = null,

	@field:SerializedName("todayCases")
	val todayCases: String? = null,

	@field:SerializedName("todayDeaths")
	val todayDeaths: String? = null
)

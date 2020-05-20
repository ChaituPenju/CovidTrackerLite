package com.chaitupenju.covidtracker.models

import com.google.gson.annotations.SerializedName

data class UnitedStateItem(

	@field:SerializedName("cases")
	val cases: Int? = null,

	@field:SerializedName("tests")
	val tests: Int? = null,

	@field:SerializedName("deathsPerOneMillion")
	val deathsPerOneMillion: Int? = null,

	@field:SerializedName("active")
	val active: Int? = null,

	@field:SerializedName("casesPerOneMillion")
	val casesPerOneMillion: Int? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("updated")
	val updated: Long? = null,

	@field:SerializedName("deaths")
	val deaths: Int? = null,

	@field:SerializedName("testsPerOneMillion")
	val testsPerOneMillion: Int? = null,

	@field:SerializedName("todayCases")
	val todayCases: Int? = null,

	@field:SerializedName("todayDeaths")
	val todayDeaths: Int? = null
)

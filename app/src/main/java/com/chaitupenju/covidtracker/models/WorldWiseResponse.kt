package com.chaitupenju.covidtracker.models

import com.google.gson.annotations.SerializedName

data class WorldWiseResponse(

	@field:SerializedName("data")
	val data: Data? = null
)

data class Data(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("confirmed_diff")
	val confirmedDiff: String? = null,

	@field:SerializedName("active_diff")
	val activeDiff: String? = null,

	@field:SerializedName("deaths_diff")
	val deathsDiff: String? = null,

	@field:SerializedName("recovered")
	val recovered: String? = null,

	@field:SerializedName("recovered_diff")
	val recoveredDiff: String? = null,

	@field:SerializedName("fatality_rate")
	val fatalityRate: Double? = null,

	@field:SerializedName("last_update")
	val lastUpdate: String? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("confirmed")
	val confirmed: String? = null,

	@field:SerializedName("deaths")
	val deaths: String? = null
)

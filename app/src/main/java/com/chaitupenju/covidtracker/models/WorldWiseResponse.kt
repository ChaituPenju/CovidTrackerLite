package com.chaitupenju.covidtracker.models

import com.google.gson.annotations.SerializedName

data class WorldWiseResponse(

	@field:SerializedName("dt")
	val dt: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("ts")
	val ts: Int? = null
)

data class Data(

	@field:SerializedName("recovered")
	val recovered: String? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("confirmed")
	val confirmed: String? = null,

	@field:SerializedName("deaths")
	val deaths: String? = null
)

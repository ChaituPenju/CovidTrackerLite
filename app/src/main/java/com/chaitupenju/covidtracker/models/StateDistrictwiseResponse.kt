package com.chaitupenju.covidtracker.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StateDistrictwiseItem(

    @field:SerializedName("districtData")
    val districtData: List<DistrictDataItem>,

    @field:SerializedName("state")
    val state: String,

    @field:SerializedName("statecode")
    val statecode: String
) : Parcelable

@Parcelize
data class DistrictDataItem(

    @field:SerializedName("recovered")
    val recovered: String? = null,

    @field:SerializedName("notes")
    val notes: String? = null,

    @field:SerializedName("deceased")
    val deceased: String? = null,

    @field:SerializedName("district")
    val district: String? = null,

    @field:SerializedName("delta")
    val delta: Delta? = null,

    @field:SerializedName("active")
    val active: String? = null,

    @field:SerializedName("confirmed")
    val confirmed: String? = null
) : Parcelable

@Parcelize
data class Delta(

    @field:SerializedName("recovered")
    val recovered: Int? = null,

    @field:SerializedName("deceased")
    val deceased: Int? = null,

    @field:SerializedName("confirmed")
    val confirmed: Int? = null
) : Parcelable

package com.chaitupenju.covidtracker.helpers

object Constants {
    const val STATE_CODE_KEY = "statecode"
    const val STATE_TOTAL_SUMMARY_KEY = "statetotals"

    const val SORT_OPTIONS_POSITION_KEY = "sortposition"

    const val WORLD_WISE_URL = "https://covid2019-api.herokuapp.com/v2/total"
    const val COUNTRY_WISE_URL = "https://api.coronatracker.com/v3/stats/worldometer/country"

    // https://coronavirus-19-api.herokuapp.com/countries // some invalid data
        // "https://corona-api.com/timeline"  // not upto date
    // "https://api.covid19api.com/summary" // not working properly(getting 429 - too many requests error, api problem)
}
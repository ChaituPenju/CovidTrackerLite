package com.chaitupenju.covidtracker.adapters

import com.chaitupenju.covidtracker.BR
import com.chaitupenju.covidtracker.R
import com.chaitupenju.covidtracker.models.CountryWiseItem

class CountryListRecyclerAdapter(private val countryList: List<CountryWiseItem>):
        GenericRecyclerAdapter<CountryWiseItem>(
            countryList,
            BR.countryItem,
            R.layout.item_countrywise_list
        )
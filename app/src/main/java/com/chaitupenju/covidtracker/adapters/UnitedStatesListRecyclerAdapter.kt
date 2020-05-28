package com.chaitupenju.covidtracker.adapters

import com.chaitupenju.covidtracker.BR
import com.chaitupenju.covidtracker.R
import com.chaitupenju.covidtracker.models.UnitedStateItem

class UnitedStatesListRecyclerAdapter(private val unitedStateList: List<UnitedStateItem>) :
        GenericRecyclerAdapter<UnitedStateItem>(
            unitedStateList,
            BR.unitedStateItem,
            R.layout.item_unitedstateswise_list
        ) {
}
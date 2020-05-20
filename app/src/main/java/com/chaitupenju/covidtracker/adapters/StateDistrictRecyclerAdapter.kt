package com.chaitupenju.covidtracker.adapters

import com.chaitupenju.covidtracker.BR
import com.chaitupenju.covidtracker.R
import com.chaitupenju.covidtracker.models.DistrictDataItem

class StateDistrictRecyclerAdapter(stateDistrictList: List<DistrictDataItem>) :
    GenericRecyclerAdapter<DistrictDataItem>(
        stateDistrictList,
        BR.stateDistrictItem,
        R.layout.item_statedistrictwise_list
    )
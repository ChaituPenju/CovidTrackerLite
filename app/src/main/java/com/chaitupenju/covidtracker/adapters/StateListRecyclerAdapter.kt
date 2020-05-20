package com.chaitupenju.covidtracker.adapters

import android.widget.Filter
import android.widget.Filterable
import com.chaitupenju.covidtracker.BR
import com.chaitupenju.covidtracker.R
import com.chaitupenju.covidtracker.models.StatewiseItem
import java.util.Locale

class StateListRecyclerAdapter(private val stateList: List<StatewiseItem>) :
    GenericRecyclerAdapter<StatewiseItem>(
        stateList,
        BR.stateItem,
        R.layout.item_statewise_list
    ), Filterable {

    var stateFilterList = emptyList<StatewiseItem>()

    init {
        stateFilterList = stateList
    }

    fun updateSortItems(type: Int) {
        when (type) {
            0 -> stateFilterList = stateList.sortedBy { it.state }
            1 -> stateFilterList = stateList.sortedByDescending { it.state }
            2 -> stateFilterList = stateList.sortedBy { it.confirmed?.toInt() }
            3 -> stateFilterList = stateList.sortedByDescending { it.confirmed?.toInt() }
            4 -> stateFilterList = stateList.sortedBy { it.deaths?.toInt() }
            5 -> stateFilterList = stateList.sortedByDescending { it.deaths?.toInt() }
        }

        super.updateListItems(stateFilterList)
    }

    private fun updateFilteredList(stateFilterList: List<StatewiseItem>) {
        super.updateListItems(stateFilterList)
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            // runs on background thread
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                stateFilterList = if (charSearch.isEmpty()) {
                    stateList
                } else {
                    stateList.filter { stateItem ->
                        stateItem.state?.toLowerCase(
                            Locale.ROOT
                        )?.contains(charSearch.toLowerCase(Locale.ROOT))!!
                    }
                }
                val filteredResults = FilterResults()
                filteredResults.values = stateFilterList

                return filteredResults
            }

            // runs on main thread
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                stateFilterList = results?.values as List<StatewiseItem>
                updateFilteredList(stateFilterList)
            }

        }
    }

}
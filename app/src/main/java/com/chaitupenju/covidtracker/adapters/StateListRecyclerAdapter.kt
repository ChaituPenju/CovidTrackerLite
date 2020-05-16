package com.chaitupenju.covidtracker.adapters

import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaitupenju.covidtracker.R
import com.chaitupenju.covidtracker.databinding.ItemStatewiseListBinding
import com.chaitupenju.covidtracker.models.StatewiseItem
import java.util.*

class StateListRecyclerAdapter(private val stateList: List<StatewiseItem>) :
    RecyclerView.Adapter<StateListRecyclerAdapter.StateListViewHolder>(), Filterable {

    var stateFilterList = emptyList<StatewiseItem>()

    init {
        stateFilterList = stateList
    }

    private lateinit var clickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(stateCode: String, recyclerPairs: Array<Pair<View, String>>)
    }

    fun setItemClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
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

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemStatewiseListBinding>(
            inflater,
            R.layout.item_statewise_list,
            parent,
            false
        )

        return StateListViewHolder(view)
    }

    override fun onBindViewHolder(holder: StateListViewHolder, position: Int) {
        holder.bind(stateFilterList[position], clickListener)
    }

    override fun getItemCount(): Int = stateFilterList.size

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
                notifyDataSetChanged()
            }

        }
    }

    inner class StateListViewHolder(private val stateListBinding: ItemStatewiseListBinding) :
        RecyclerView.ViewHolder(stateListBinding.root) {
        fun bind(
            statewiseitem: StatewiseItem,
            clickListener: OnItemClickListener
        ) {
            stateListBinding.stateItem = statewiseitem

            val recyclerPairs = arrayOf(
                Pair(stateListBinding.tvStatename as View, "statename"),
                Pair(stateListBinding.tvConfirmedItem as View, "totalconfirmed"),
                Pair(stateListBinding.tvActiveItem as View, "totalactive"),
                Pair(stateListBinding.tvDeathsItem as View, "totaldeaths"),
                Pair(stateListBinding.tvRecoveredItem as View, "totalrecovered")
            )

            stateListBinding.root.setOnClickListener {
                clickListener.onItemClick(statewiseitem.statecode!!, recyclerPairs)
            }

            stateListBinding.executePendingBindings()

        }
    }

}
package com.chaitupenju.covidtracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaitupenju.covidtracker.R
import com.chaitupenju.covidtracker.databinding.ItemStatedistrictwiseListBinding
import com.chaitupenju.covidtracker.models.DistrictDataItem

class StateDistrictRecyclerAdapter(private val stateDistrictList: List<DistrictDataItem>) :
    RecyclerView.Adapter<StateDistrictRecyclerAdapter.StateDistrictViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateDistrictViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemStatedistrictwiseListBinding>(
            inflater,
            R.layout.item_statedistrictwise_list,
            parent,
            false
        )
        return StateDistrictViewHolder(view)
    }

    override fun onBindViewHolder(holder: StateDistrictViewHolder, position: Int) {
        holder.bind(stateDistrictList[position])
    }

    override fun getItemCount(): Int = stateDistrictList.size


    inner class StateDistrictViewHolder(private val stateDistrictBinding: ItemStatedistrictwiseListBinding) :
        RecyclerView.ViewHolder(stateDistrictBinding.root) {

        fun bind(stateDistrictItem: DistrictDataItem) {
            stateDistrictBinding.stateDistrictItem = stateDistrictItem

            stateDistrictBinding.executePendingBindings()
        }

    }


}
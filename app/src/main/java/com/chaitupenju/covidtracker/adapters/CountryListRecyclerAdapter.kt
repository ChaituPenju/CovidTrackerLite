package com.chaitupenju.covidtracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaitupenju.covidtracker.R
import com.chaitupenju.covidtracker.databinding.ItemCountrywiseListBinding
import com.chaitupenju.covidtracker.models.CountryWiseItem

class CountryListRecyclerAdapter(private val countryList: List<CountryWiseItem>) :
    RecyclerView.Adapter<CountryListRecyclerAdapter.CountryListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = DataBindingUtil.inflate<ItemCountrywiseListBinding>(
            inflater,
            R.layout.item_countrywise_list,
            parent,
            false
        )

        return CountryListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.bind(countryList[position])
    }

    override fun getItemCount(): Int = countryList.size


    inner class CountryListViewHolder(val countryListBinding: ItemCountrywiseListBinding) :
        RecyclerView.ViewHolder(countryListBinding.root) {

        fun bind(countryItem: CountryWiseItem) {
            countryListBinding.countryItem = countryItem

            countryListBinding.executePendingBindings()
        }

    }


}
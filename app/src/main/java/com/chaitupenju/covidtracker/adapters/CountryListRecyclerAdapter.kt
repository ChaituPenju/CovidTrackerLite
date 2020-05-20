package com.chaitupenju.covidtracker.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaitupenju.covidtracker.CountryStateDetailActivity
import com.chaitupenju.covidtracker.CovidActivity
import com.chaitupenju.covidtracker.R
import com.chaitupenju.covidtracker.databinding.ItemCountrywiseListBinding
import com.chaitupenju.covidtracker.models.CountryWiseItem
import kotlin.coroutines.coroutineContext

class CountryListRecyclerAdapter(
    private val countryList: List<CountryWiseItem>,
    private val ctx: Context
) :
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

            countryListBinding.root.setOnClickListener {
                if (adapterPosition == 0) {
                    println("CLICKED ON USA")
                    val intent = Intent(ctx, CountryStateDetailActivity::class.java)
                    ctx.startActivity(intent)
                } else {
                    Toast.makeText(ctx, "Oops!! Data Not Present!", Toast.LENGTH_LONG).show()
                }
            }

            countryListBinding.executePendingBindings()
        }

    }


}
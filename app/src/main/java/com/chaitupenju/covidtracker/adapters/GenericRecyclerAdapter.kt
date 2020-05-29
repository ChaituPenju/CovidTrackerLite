package com.chaitupenju.covidtracker.adapters


import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.chaitupenju.covidtracker.databinding.ItemCountrywiseListBinding
import com.chaitupenju.covidtracker.databinding.ItemStatedistrictwiseListBinding
import com.chaitupenju.covidtracker.databinding.ItemStatewiseListBinding
import com.chaitupenju.covidtracker.models.CountryWiseItem
import com.chaitupenju.covidtracker.models.StatewiseItem

open class GenericRecyclerAdapter<T>(private var listItems: List<T>, private val bindable: Int, @LayoutRes val layoutId: Int) :
    RecyclerView.Adapter<GenericRecyclerAdapter.GenericViewHolder<T>>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(bindClickListener: OnItemClickListener?){
        this.itemClickListener = bindClickListener
    }

    fun updateListItems(updatedList: List<T>) {
        this.listItems = updatedList

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        return GenericViewHolder(binding, bindable)
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {

        holder.bind(listItems[position], itemClickListener)
    }


    class GenericViewHolder<T>(private val binding: ViewDataBinding, private val bindableItem: Int) :
        RecyclerView.ViewHolder(binding.root) {
        var recyclerPairs:Array<Pair<View, String>>? = null

        fun bind(
            listItem: T,
            bindClickListener: OnItemClickListener?
        ) {
            binding.setVariable(bindableItem, listItem)
            when (binding) {
                is ItemStatewiseListBinding -> {
                    recyclerPairs = arrayOf(
                        Pair(binding.itemStatewise.tvStateCountry as View, "statecountryname"),
                        Pair(binding.itemStatewise.tvConfirmedItem as View, "totalconfirmed"),
                        Pair(binding.itemStatewise.tvActiveItem as View, "totalactive"),
                        Pair(binding.itemStatewise.tvDeathsItem as View, "totaldeaths"),
                        Pair(binding.itemStatewise.tvRecoveredItem as View, "totalrecovered")
                    )
                }
                is ItemStatedistrictwiseListBinding -> {
                    binding.itemStatedistrictwise.tvStateCountry.rotation = -20f
                }
                is ItemCountrywiseListBinding -> {
                    recyclerPairs = arrayOf(
                        Pair(binding.itemUsStatewise.tvStateCountry as View, "statecountryname"),
                        Pair(binding.itemUsStatewise.tvConfirmedItem as View, "totalconfirmed"),
                        Pair(binding.itemUsStatewise.tvActiveItem as View, "totalactive"),
                        Pair(binding.itemUsStatewise.tvDeathsItem as View, "totaldeaths"),
                        Pair(binding.itemUsStatewise.tvRecoveredItem as View, "totalrecovered")
                    )

                }
            }

            binding.root.setOnClickListener {
                when (listItem) {
                    is StatewiseItem -> {
                        bindClickListener?.onClick(listItem.statecode, recyclerPairs)
                    }
                    is CountryWiseItem -> {
                        bindClickListener?.onClick(adapterPosition, recyclerPairs)
                    }
                }
//                bindClickListener?.onClick(listItem)
            }

            binding.executePendingBindings()
        }

    }

    interface OnItemClickListener{
        fun onClick(vararg item: Any?)
    }
}
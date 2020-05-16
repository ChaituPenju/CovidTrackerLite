package com.chaitupenju.covidtracker.adapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("setSelectable")
fun setSelected(view: View, selected: Boolean) {
    view.isSelected = selected
}
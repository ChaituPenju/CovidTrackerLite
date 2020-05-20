package com.chaitupenju.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chaitupenju.covidtracker.databinding.ActivityCountryStateDetailBinding
import com.chaitupenju.covidtracker.network.RetrofitBuilder
import com.chaitupenju.covidtracker.viewmodels.CountryStateViewModel
import com.chaitupenju.covidtracker.viewmodels.ViewModelFactory

class CountryStateDetailActivity : AppCompatActivity() {

    private lateinit var csda: ActivityCountryStateDetailBinding
    private lateinit var viewModel: CountryStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        csda = DataBindingUtil.setContentView(this, R.layout.activity_country_state_detail)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(RetrofitBuilder.apiService, "CountryStateActivity")
        ).get(CountryStateViewModel::class.java)

        viewModel.getCountryUSAStateWiseResponse().observe(this, Observer {
            val adapter = null
            csda.rvCountrystatelist.adapter
        })

    }
}

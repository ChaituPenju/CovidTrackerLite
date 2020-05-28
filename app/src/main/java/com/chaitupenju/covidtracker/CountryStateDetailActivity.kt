package com.chaitupenju.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chaitupenju.covidtracker.adapters.UnitedStatesListRecyclerAdapter
import com.chaitupenju.covidtracker.databinding.ActivityCountryStateDetailBinding
import com.chaitupenju.covidtracker.databinding.ContentTotalcasesChartBinding
import com.chaitupenju.covidtracker.helpers.Constants
import com.chaitupenju.covidtracker.helpers.Helper
import com.chaitupenju.covidtracker.models.UnitedStateItem
import com.chaitupenju.covidtracker.network.RetrofitBuilder
import com.chaitupenju.covidtracker.viewmodels.CountryStateViewModel
import com.chaitupenju.covidtracker.viewmodels.ViewModelFactory

class CountryStateDetailActivity : AppCompatActivity() {

    private lateinit var csda: ActivityCountryStateDetailBinding
    private lateinit var viewModel: CountryStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        csda = DataBindingUtil.setContentView(this, R.layout.activity_country_state_detail)

        val intent = intent.extras
        val totalCases = intent?.getIntArray(Constants.COUNTRY_US_TOTAL_SUMMARY_KEY)

        setupChartView(csda.inclTotalcasesChartcountry, totalCases!!)
        setupTotalTexts(totalCases)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(RetrofitBuilder.apiService, "CountryStateActivity")
        ).get(CountryStateViewModel::class.java)

        viewModel.getCountryUSAStateWiseResponse().observe(this, Observer {
            setupRecyclerView(it)
        })
    }

    private fun setupChartView(tccb: ContentTotalcasesChartBinding, totals: Any) {
        val pieChart = tccb.pieTotalcases
        var descText = ""
        pieChart.setUsePercentValues(true)
        val data = Helper.getTotalCasesPieChartData(baseContext, totals)
        data.setValueTextSize(14f)

        descText = "United States - TOTAL CASES"
        pieChart.description.text = descText
        pieChart.description.textSize = 10f
        pieChart.data = data
        pieChart.setTransparentCircleAlpha(0)
        pieChart.holeRadius = 20f
        pieChart.animateY(1000)
    }

    private fun setupTotalTexts(totalCases: IntArray) {
        csda.inclTotalcasesCountry.tvActive.text = totalCases[0].toString()
        csda.inclTotalcasesCountry.tvDeaths.text = totalCases[1].toString()
        csda.inclTotalcasesCountry.tvRecovered.text = totalCases[2].toString()
        csda.inclTotalcasesCountry.tvConfirmed.text = totalCases[3].toString()
    }

    private fun setupRecyclerView(unitedStatesList: List<UnitedStateItem>) {
        val adapterU = UnitedStatesListRecyclerAdapter(unitedStatesList)

        csda.rvCountrystatelist.apply {
            adapter = adapterU
        }
    }
}

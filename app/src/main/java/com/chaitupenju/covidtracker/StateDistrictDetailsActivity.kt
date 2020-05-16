package com.chaitupenju.covidtracker

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaitupenju.covidtracker.adapters.StateDistrictRecyclerAdapter
import com.chaitupenju.covidtracker.databinding.ActivityStateDistrictDetailsBinding
import com.chaitupenju.covidtracker.helpers.Constants
import com.chaitupenju.covidtracker.helpers.Constants.STATE_CODE_KEY
import com.chaitupenju.covidtracker.models.DistrictDataItem
import com.chaitupenju.covidtracker.network.RetrofitBuilder
import com.chaitupenju.covidtracker.viewmodels.StateDistrictViewModel
import com.chaitupenju.covidtracker.viewmodels.ViewModelFactory

class StateDistrictDetailsActivity : AppCompatActivity() {
    private lateinit var asdb: ActivityStateDistrictDetailsBinding
    private lateinit var viewModel: StateDistrictViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        asdb = DataBindingUtil.setContentView(this, R.layout.activity_state_district_details)
        setupViewModel()
        retrieveData()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                println("Home Pressed")
                super.onBackPressed()
                return true
            }
        }
        return true
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(RetrofitBuilder.apiService, "StateDistrictActivity")
        ).get(StateDistrictViewModel::class.java)
    }

    private fun retrieveData() {
        val intent = intent.extras
        val statecode = intent.let { it?.getString(STATE_CODE_KEY, "AP") }
        val summarycases = intent.let { it?.getStringArray(Constants.STATE_TOTAL_SUMMARY_KEY) } ?: arrayOf("0", "0", "0", "0")
        setupTotalCases(summarycases)

        viewModel.getStateDistrictWiseResponse().observe(this, Observer {
            val stateDistrictItem =
                it.filter { districtItem -> districtItem.statecode == statecode }[0]
            println(stateDistrictItem)
            setupRecyclerView(stateDistrictItem.districtData)
            asdb.tvStatenamehead.text = stateDistrictItem.state
        })
    }

    private fun setupTotalCases(summaries: Array<String>) {
        asdb.inclTotalcases.tvConfirmed.text = summaries[0]
        asdb.inclTotalcases.tvActive.text = summaries[1]
        asdb.inclTotalcases.tvDeaths.text = summaries[2]
        asdb.inclTotalcases.tvRecovered.text = summaries[3]
    }

    private fun setupRecyclerView(districtData: List<DistrictDataItem>) {
        asdb.rvStatedistrictlist.apply {
            adapter = StateDistrictRecyclerAdapter(districtData)
//            addItemDecoration(
//                DividerItemDecoration(
//                    this@StateDistrictDetailsActivity,
//                    LinearLayoutManager.VERTICAL
//                )
//            )
        }
    }
}

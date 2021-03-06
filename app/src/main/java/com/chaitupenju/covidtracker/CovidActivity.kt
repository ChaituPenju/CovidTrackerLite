package com.chaitupenju.covidtracker

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.preference.PreferenceManager
import android.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chaitupenju.covidtracker.adapters.CountryListRecyclerAdapter
import com.chaitupenju.covidtracker.adapters.GenericRecyclerAdapter
import com.chaitupenju.covidtracker.adapters.StateListRecyclerAdapter
import com.chaitupenju.covidtracker.databinding.ActivityCovidBinding
import com.chaitupenju.covidtracker.databinding.ContentTotalcasesChartBinding
import com.chaitupenju.covidtracker.databinding.ContentTotalcasesViewBinding
import com.chaitupenju.covidtracker.helpers.Constants
import com.chaitupenju.covidtracker.helpers.Constants.COUNTRY_US_TOTAL_SUMMARY_KEY
import com.chaitupenju.covidtracker.helpers.Constants.STATE_CODE_KEY
import com.chaitupenju.covidtracker.helpers.Constants.STATE_TOTAL_SUMMARY_KEY
import com.chaitupenju.covidtracker.helpers.Helper
import com.chaitupenju.covidtracker.models.*
import com.chaitupenju.covidtracker.network.RetrofitBuilder
import com.chaitupenju.covidtracker.viewmodels.MainViewModel
import com.chaitupenju.covidtracker.viewmodels.ViewModelFactory
import com.miguelcatalan.materialsearchview.MaterialSearchView
import java.util.*

class CovidActivity : AppCompatActivity() {
    private lateinit var amb: ActivityCovidBinding
    private lateinit var stateListAdapter: StateListRecyclerAdapter
    private lateinit var countryListAdapter: CountryListRecyclerAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var materialSearchView: MaterialSearchView
    private lateinit var menuList: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        amb = DataBindingUtil.setContentView(this, R.layout.activity_covid)
        materialSearchView = amb.searchStates

        setSupportActionBar(amb.toolbar)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(RetrofitBuilder.apiService, "MainActivity")
        ).get(MainViewModel::class.java)

        setupSearchViewListeners()
        setupChronometer()
        setupDataSwitcherX()
        setupTypeTextAnimation()

        val topCountries =
            arrayOf(
                "USA", "RUSSIA", "UK", "ITALY", "FRANCE", "GERMANY", "CANADA", "SINGAPORE",
                "SRI LANKA", "MALAYSIA", "SPAIN", "ITALY"
            )

        viewModel.getStateWiseResponse().observe(this, Observer {
            updateIndiaUI(it)
        })
        viewModel.getWorldWiseResponse().observe(this, Observer {
            updateWorldUI(it)
        })

        viewModel.getCountryWiseResponse().observe(this, Observer {
            val countriesFiltered =
                it.filter { countries -> countries.country?.toUpperCase(Locale.ROOT) in topCountries }

            updateCountriesUI(countriesFiltered)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_sort, menu)
        this.menuList = menu!!
        if (amb.switchState!!) {
            println("IT IS CHANGING")
        } else {
            println("IT IS NOT CHANGING")
        }

        val item = menu.findItem(R.id.action_search)
        materialSearchView.setMenuItem(item)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                amb.nsvApp.smoothScrollTo(0, amb.inclHeader.root.top)
            }
            R.id.action_sort -> {
                amb.nsvApp.smoothScrollTo(0, amb.inclHeader.root.top)
            }
            R.id.action_sort_by -> {
                val sortDialog =
                    Helper.getAlertDialog(
                        this@CovidActivity,
                        object : Helper.OnDialogClickListener {
                            override fun onDialogOptionClick(position: Int) {
                                Helper.saveSortOptionPosition(this@CovidActivity, position)
                                stateListAdapter.updateSortItems(position)
                            }

                        })
                sortDialog?.show()
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (materialSearchView.isSearchOpen) {
            amb.nsvApp.smoothScrollTo(0, 0)
            materialSearchView.closeSearch()
        } else {
            super.onBackPressed()
        }
    }

    private fun showHideMenu(isShow: Boolean) {
        val item1 = menuList.findItem(R.id.action_search)
        val item2 = menuList.findItem(R.id.action_sort)

        item1.isVisible = !isShow
        item2.isVisible = !isShow
    }

    private fun setupDataSwitcherX() {
        amb.switchState = false

        amb.swchIndworld.setOnCheckedChangeListener { checked ->
            amb.switchState = checked
            showHideMenu(checked)
            if (checked) {
                amb.efvTotalcases.flipTheView()
                amb.efvPiechart.flipTheView()
            } else {
                amb.efvPiechart.flipTheView()
                amb.efvTotalcases.flipTheView()
            }
        }
    }

    private fun setupTypeTextAnimation() {
        val typeWriterView = amb.typeWriterView
        typeWriterView.setDelay(150)
        typeWriterView.setWithMusic(false)
        typeWriterView.animateText("Stay Home! Stay Safe!! :)")
    }

    private fun setupChronometer() {
        val lockdownChrono = amb.chroTimelockdown

        lockdownChrono.setOnChronometerTickListener { chronometer ->
            chronometer.text = Helper.getChronometerTimeString(chronometer.base)
        }

        lockdownChrono.base = SystemClock.elapsedRealtime() - Helper.currentMillisSinceLockdown()
        lockdownChrono.start()
    }

    private fun setupSearchViewListeners() {
        materialSearchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                amb.nsvApp.smoothScrollTo(0, amb.inclHeader.root.top)
            }

            override fun onSearchViewClosed() {
                amb.nsvApp.smoothScrollTo(0, 0)
            }
        })

        materialSearchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                amb.nsvApp.isSmoothScrollingEnabled = false
                amb.nsvApp.isNestedScrollingEnabled = false
                stateListAdapter.filter.filter(newText)
                return false
            }

        })
    }

    private fun sharedElementTransitions(recyclerPairs: Array<Pair<View, String>>): Bundle? {
        val pairs = arrayOf(
            Pair(amb.inclHeader.tvConfirmedHead as View, "CONFIRMED"),
            Pair(amb.inclHeader.tvActiveHead as View, "ACTIVE"),
            Pair(amb.inclHeader.tvDeathsHead as View, "DEATHS"),
            Pair(amb.inclHeader.tvRecoveredHead as View, "RECOVERED")
        )

        val options = ActivityOptions.makeSceneTransitionAnimation(
            this, *pairs, *recyclerPairs
        )

        return options.toBundle()
    }

    private fun updateIndiaUI(itemResponse: StateWiseResponse) {
        val totalItem: StatewiseItem = itemResponse.statewise[0]
        setUpTotalTexts(amb.inclTotalcases, totalItem) // set this card for india cases

        setupChartView(amb.inclTotalcasesChart, totalItem) // set this pie chart for india cases

        setupRecyclerView(itemResponse.statewise.subList(1, itemResponse.statewise.size - 1))
    }

    private fun updateWorldUI(globalResponse: WorldWiseResponse) {
        val totalItem: Data? = globalResponse.data
        setUpTotalTexts(amb.inclTotalcasesWorld, totalItem!!) // set this card for world cases

        setupChartView(
            amb.inclTotalcasesChartworld,
            totalItem
        ) // set this pie chart for world cases
    }

    private fun updateCountriesUI(countryItems: List<CountryWiseItem>) {
        countryListAdapter = CountryListRecyclerAdapter(countryItems)

        countryListAdapter.setOnItemClickListener(object :
            GenericRecyclerAdapter.OnItemClickListener {
            override fun onClick(vararg item: Any?) {
                val adapterPosition = item[0] as Int
                val recyclerPairs = item[1] as Array<Pair<View, String>>
                val summaryTotals = countryItems[0]
                if (adapterPosition == 0) {
                    val intent = Intent(this@CovidActivity, CountryStateDetailActivity::class.java)
                    intent.putExtra(
                        COUNTRY_US_TOTAL_SUMMARY_KEY,
                        intArrayOf(
                            summaryTotals.activeCases?.toInt()!!,
                            summaryTotals.totalDeaths?.toInt()!!,
                            summaryTotals.totalRecovered?.toInt()!!,
                            summaryTotals.totalConfirmed?.toInt()!!
                        )
                    )
                    startActivity(intent, sharedElementTransitions(recyclerPairs))
                } else {
                    Toast.makeText(
                        this@CovidActivity,
                        "Oops!! Data Not Present!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })

        amb.rvCountrywiselist.apply {
            adapter = countryListAdapter
        }
    }

    private fun setUpTotalTexts(tcb: ContentTotalcasesViewBinding, itemAny: Any) {
        when (itemAny) {
            is StatewiseItem -> {
                tcb.tvConfirmed.text = Helper.getSpannedText(
                    baseContext,
                    "${itemAny.confirmed}\n ↑ ${itemAny.deltaconfirmed ?: ""}",
                    R.color.primary_blue,
                    itemAny.confirmed?.length ?: 0
                )
                tcb.tvActive.text = itemAny.active
                tcb.tvDeaths.text = Helper.getSpannedText(
                    baseContext,
                    "${itemAny.deaths}\n ↑ ${itemAny.deltadeaths ?: ""}",
                    R.color.danger_red,
                    itemAny.deaths?.length ?: 0
                )
                tcb.tvRecovered.text = Helper.getSpannedText(
                    baseContext,
                    "${itemAny.recovered}\n ↑ ${itemAny.deltarecovered ?: ""}",
                    R.color.success_green,
                    itemAny.recovered?.length ?: 0
                )
            }
            is Data -> {
                tcb.tvConfirmed.text = Helper.getSpannedText(
                    baseContext,
                    "${itemAny.confirmed}\n ↑ ${itemAny.confirmedDiff ?: ""}",
                    R.color.primary_blue,
                    itemAny.confirmed?.length ?: 0
                )
                tcb.tvActive.text = itemAny.active
                tcb.tvDeaths.text = Helper.getSpannedText(
                    baseContext,
                    "${itemAny.deaths}\n ↑ ${itemAny.deathsDiff ?: ""}",
                    R.color.danger_red,
                    itemAny.deaths?.length ?: 0
                )
                tcb.tvRecovered.text = Helper.getSpannedText(
                    baseContext,
                    "${itemAny.recovered}\n ↑ ${itemAny.recoveredDiff ?: ""}",
                    R.color.success_green,
                    itemAny.recovered?.length ?: 0
                )
            }
        }

    }

    private fun setupChartView(tccb: ContentTotalcasesChartBinding, totals: Any) {
        val pieChart = tccb.pieTotalcases
        var descText = ""
        pieChart.setUsePercentValues(true)
        val data = Helper.getTotalCasesPieChartData(baseContext, totals)
        data.setValueTextSize(14f)
        when (totals) {
            is StatewiseItem -> {
                descText = "INDIA - TOTAL CASES"
            }
            is Data -> {
                descText = "WORLD - TOTAL CASES"
            }
        }
        pieChart.description.text = descText
        pieChart.description.textSize = 10f
        pieChart.data = data
        pieChart.setTransparentCircleAlpha(0)
        pieChart.holeRadius = 20f
        pieChart.animateY(1000)
    }

    private fun setupRecyclerView(stateList: List<StatewiseItem>) {
        stateListAdapter = StateListRecyclerAdapter(stateList)

        stateListAdapter.setOnItemClickListener(object :
            GenericRecyclerAdapter.OnItemClickListener {
            override fun onClick(vararg item: Any?) {
                val stateCode = item[0] as String
                val recyclerPairs = item[1] as Array<Pair<View, String>>

                val summaryCases =
                    stateList.filter { stateItem -> stateItem.statecode == stateCode }[0]
                val intent = Intent(this@CovidActivity, StateDistrictDetailsActivity::class.java)
                intent.putExtra(STATE_CODE_KEY, stateCode)
                intent.putExtra(
                    STATE_TOTAL_SUMMARY_KEY,
                    arrayOf(
                        summaryCases.confirmed,
                        summaryCases.active,
                        summaryCases.deaths,
                        summaryCases.recovered
                    )
                )
                startActivity(intent, sharedElementTransitions(recyclerPairs))
            }

        })

        amb.rvStatewiselist.apply {
            adapter = stateListAdapter
        }

        val checkedItem = PreferenceManager.getDefaultSharedPreferences(this)
            .getInt(Constants.SORT_OPTIONS_POSITION_KEY, 2)
        stateListAdapter.updateSortItems(checkedItem)

        amb.rvStatewiselist.setHasFixedSize(true)
        val anim = AnimationUtils.loadLayoutAnimation(
            this@CovidActivity,
            R.anim.layout_animation_fall_down
        )
        amb.rvStatewiselist.layoutAnimation = anim
        amb.rvStatewiselist.scheduleLayoutAnimation()
    }
}

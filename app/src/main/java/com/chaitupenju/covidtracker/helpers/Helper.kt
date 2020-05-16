package com.chaitupenju.covidtracker.helpers

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.SystemClock
import android.preference.PreferenceManager
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.chaitupenju.covidtracker.R
import com.chaitupenju.covidtracker.models.Data
import com.chaitupenju.covidtracker.models.StatewiseItem
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.text.SimpleDateFormat
import java.util.*


object Helper {

    fun currentMillisSinceLockdown(): Long {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH)
        val lockdownStartDate = simpleDateFormat.parse("24-03-2020 18:04:00").time
        val currentMillis = System.currentTimeMillis()

        return currentMillis - lockdownStartDate
    }

    fun getChronometerTimeString(chronoBase: Long): String {
        val time = SystemClock.elapsedRealtime() - chronoBase
        val d = (time / 86400000).toInt()
        val hh = (time - d * 86400000).toInt() / 3600000
        val h = (time / 3600000).toInt()
        val m = (time - h * 3600000).toInt() / 60000
        val s = (time - h * 3600000 - m * 60000).toInt() / 1000

        return (if (d < 10) "0$d" else d).toString() + ":" + (if (hh < 10) "0$hh" else hh).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10) "0$s" else s
    }

    fun getTotalCasesPieChartData(context: Context, totalsAny: Any): PieData {
        var yValues: MutableList<PieEntry>? = null
        when (totalsAny) {
            is StatewiseItem -> {
                val totals = totalsAny as StatewiseItem
                yValues = mutableListOf(
                    PieEntry(totals.confirmed?.toFloat()!!, "CONFIRMED"),
                    PieEntry(totals.active?.toFloat()!!, "ACTIVE"),
                    PieEntry(totals.deaths?.toFloat()!!, "DEATHS"),
                    PieEntry(totals.recovered?.toFloat()!!, "RECOVERED")
                )
            }
            is Data -> {
                val totals = totalsAny as Data
                yValues = mutableListOf(
                    PieEntry(totals.confirmed?.toFloat()!!, "CONFIRMED"),
                    PieEntry(totals.active?.toFloat()!!, "ACTIVE"),
                    PieEntry(totals.deaths?.toFloat()!!, "DEATHS"),
                    PieEntry(totals.recovered?.toFloat()!!, "RECOVERED")
                )
            }
        }


        val dataSet = PieDataSet(yValues, "")

        val colorFirst = context.let { ContextCompat.getColor(it, R.color.primary_blue) }
        val colorSecond = context.let { ContextCompat.getColor(it, R.color.success_green) }
        val colorThird = context.let { ContextCompat.getColor(it, R.color.danger_red) }
        val colorFourth = context.let { ContextCompat.getColor(it, R.color.warning_yellow) }

        dataSet.setColors(colorFirst, colorSecond, colorThird, colorFourth)

        val data = PieData(dataSet)
        data.setValueTextSize(14f)
        data.setValueFormatter(PercentFormatter())

        return data
    }

    fun getSpannedText(context: Context, text: String, color: Int, start: Int): SpannableDelta {
        return SpannableDelta(context, text, color, start)
    }

    interface OnDialogClickListener {
        fun onDialogOptionClick(position: Int)
    }

    fun saveSortOptionPosition(context: Context, pos: Int) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putInt(Constants.SORT_OPTIONS_POSITION_KEY, pos)
            .apply()
    }

    fun getAlertDialog(context: Context, clickListener: OnDialogClickListener): AlertDialog? {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Sort By...")
        val items = arrayOf(
            "Sort By State/UT",
            "Sort By State/UT Descending",
            "Sort By Confirmed",
            "Sort By Confirmed Descending",
            "Sort By Deaths",
            "Sort By Deaths Descending"
        )
        val checkedItem = PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.SORT_OPTIONS_POSITION_KEY, 2)

        alertDialog.setSingleChoiceItems(items, checkedItem) { dialog, which ->
            dialog.dismiss()
            clickListener.onDialogOptionClick(which)
        }

        alertDialog.setNegativeButton("Cancel") { _, _ -> }
        val alertDlg = alertDialog.create()
        alertDlg.setCanceledOnTouchOutside(true)
        return alertDlg
    }

    class SpannableDelta(context: Context, text: String, color: Int, start: Int) :
        SpannableString(text) {
        init {
            setSpan(
                ForegroundColorSpan(context.let { ContextCompat.getColor(it, color) }),
                start,
                text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}
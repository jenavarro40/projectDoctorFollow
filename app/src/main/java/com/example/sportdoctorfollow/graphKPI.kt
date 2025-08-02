package com.example.sportdoctorfollow

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import com.github.mikephil.charting.charts.LineChart
import androidx.core.graphics.toColorInt


class graphKPI {
    fun graphKPI(
        context: Context,
        lineChart: LineChart,
        userKpi: List<InsertKpi>,
        //graphNumber: Int,
        kpiName1: String,
        kpiName2: String

    ) {
        val dateData: MutableList<String> = mutableListOf()
        val dateParam1: MutableList<Float> = mutableListOf()
        val dateParam2: MutableList<Float> = mutableListOf()
        val formatdate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        for (pac in userKpi) {
            val localDateTime =
                pac.date.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
            //Toast.makeText(context, localDateTime.format(formatdate), Toast.LENGTH_SHORT).show()
            dateData.add(localDateTime.format(formatdate))
            if (kpiName1 == "Diastole") {
                dateParam1.add(pac.bloodPreasureDia.toFloat())
                dateParam2.add(pac.bloodPreasureSis.toFloat())
            }
            else if (kpiName1 == "Weight"){
                dateParam1.add(pac.weight.toFloat())
            }
            else if (kpiName1 == "Calories"){
                dateParam1.add(pac.calories.toFloat())
            }
            else if (kpiName1 == "Fat(%)"){
                dateParam1.add(pac.fatRate.toFloat())
            }
            else if (kpiName1 == "Heart Rate"){
                dateParam1.add(pac.heartRate.toFloat())
            }
        }



        var entries1 = ArrayList<Entry>()
        var entries2 = ArrayList<Entry>()
        for ((index, value) in dateParam1.withIndex()) {
            entries1.add(Entry(index.toFloat(), value))
            if (kpiName1 == "Diastole") {
                entries2.add(Entry(index.toFloat(), dateParam2[index]))
            }
        }

        lineChart.setBackgroundColor(Color.WHITE)
        val dataSet1 = LineDataSet(entries1,kpiName1)
        dataSet1.color = "#003f88".toColorInt()
        dataSet1.setCircleColor("#64b5f6".toColorInt())
        dataSet1.lineWidth = 2f
        dataSet1.circleRadius = 5f
        dataSet1.setDrawValues(false)

        val lineData: LineData = if (kpiName1 == "Diastole") {
            val dataSet2 = LineDataSet(entries2,kpiName2)
            dataSet2.color = "#ff6f00".toColorInt()
            dataSet2.setCircleColor("#ffcc80".toColorInt())
            dataSet2.lineWidth = 2f
            dataSet2.circleRadius = 5f
            dataSet2.setDrawValues(false)
            LineData(dataSet1, dataSet2)
        } else {
            LineData(dataSet1)
        }

        lineChart.data = lineData
        lineChart.legend.textColor = Color.BLACK
        lineChart.xAxis.textColor = Color.BLACK
        lineChart.axisLeft.textColor = Color.BLACK
        lineChart.axisRight.isEnabled = false


        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(dateData)
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        lineChart.invalidate()
    }


}

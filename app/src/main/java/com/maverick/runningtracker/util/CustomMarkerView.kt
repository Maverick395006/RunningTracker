package com.maverick.runningtracker.util

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.maverick.runningtracker.db.Run
import kotlinx.android.synthetic.main.marker_view.view.tvAvgSpeed
import kotlinx.android.synthetic.main.marker_view.view.tvCaloriesBurned
import kotlinx.android.synthetic.main.marker_view.view.tvDate
import kotlinx.android.synthetic.main.marker_view.view.tvDistance
import kotlinx.android.synthetic.main.marker_view.view.tvDuration
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CustomMarkerView(
    val runs: List<Run>,
    c: Context,
    layoutId: Int
) : MarkerView(c, layoutId) {

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) {
            return
        }
        val curRunId = e.x.toInt()
        val currentRun = runs[curRunId]

        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentRun.timeStamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        tvDate.text = dateFormat.format(calendar.time)

        val avgSpeed = "${currentRun.avgSpeedInKMH}km/h"
        tvAvgSpeed.text = avgSpeed

        val distanceInKm = "${currentRun.distanceInMeters / 1000f}km"
        tvDistance.text = distanceInKm

        tvDuration.text = TrackingUtility.getFormattedStopWatchTime(currentRun.timeInMillis)

        val caloriesBurned = "${currentRun.caloriesBurned}kcal"
        tvCaloriesBurned.text = caloriesBurned
    }

}
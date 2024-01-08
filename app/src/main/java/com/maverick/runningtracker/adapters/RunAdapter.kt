package com.maverick.runningtracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maverick.runningtracker.R
import com.maverick.runningtracker.db.Run
import com.maverick.runningtracker.util.TrackingUtility
import kotlinx.android.synthetic.main.item_run.view.ivRunImage
import kotlinx.android.synthetic.main.item_run.view.tvAvgSpeed
import kotlinx.android.synthetic.main.item_run.view.tvCalories
import kotlinx.android.synthetic.main.item_run.view.tvDate
import kotlinx.android.synthetic.main.item_run.view.tvDistance
import kotlinx.android.synthetic.main.item_run.view.tvTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_run,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val currentRun = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(currentRun.img).into(ivRunImage)

            val calendar = Calendar.getInstance().apply {
                timeInMillis = currentRun.timeStamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            tvDate.text = dateFormat.format(calendar.time)

            val avgSpeed = "${currentRun.avgSpeedInKMH}km/h"
            tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${currentRun.distanceInMeters / 1000f}km"
            tvDistance.text = distanceInKm

            tvTime.text = TrackingUtility.getFormattedStopWatchTime(currentRun.timeInMillis)

            val caloriesBurned = "${currentRun.caloriesBurned}kcal"
            tvCalories.text = caloriesBurned
        }
    }

}
package com.lauruscorp.kmm_example.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lauruscorp.kmm_example.data.entities.RocketLaunch

class RocketLaunchesAdapter(
	var launches: List<RocketLaunch>
) : RecyclerView.Adapter<RocketLaunchesAdapter.RocketLaunchViewHolder>() {
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketLaunchViewHolder {
		val itemView = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_launch, parent, false)
		
		return RocketLaunchViewHolder(itemView)
	}
	
	override fun onBindViewHolder(holder: RocketLaunchViewHolder, position: Int) {
		val rocketLaunch = launches[position]
		holder.bind(rocketLaunch)
	}
	
	override fun getItemCount(): Int = launches.count()
	
	inner class RocketLaunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val missionNameTextView = itemView.findViewById<TextView>(R.id.missionName)
		val launchSuccessTextView = itemView.findViewById<TextView>(R.id.launchSuccess)
		val launchYearTextView = itemView.findViewById<TextView>(R.id.launchYear)
		val missionDetailsTextView = itemView.findViewById<TextView>(R.id.details)
		
		fun bind(rocketLaunch: RocketLaunch) {
			val ctx = itemView.context
			missionNameTextView.text = ctx.getString(R.string.mission_name_field, rocketLaunch.missionName)
			launchYearTextView.text = ctx.getString(R.string.launch_year_field, rocketLaunch.launchYear.toString())
			missionDetailsTextView.text = ctx.getString(R.string.details_field, rocketLaunch.details ?: "")
			
			val launchSuccess = rocketLaunch.launchSuccess
			if (launchSuccess != null) {
				if (launchSuccess) {
					launchSuccessTextView.text = ctx.getString(R.string.successful)
					launchSuccessTextView.setTextColor(
						ContextCompat.getColor(
							itemView.context,
							R.color.colorSuccessful
						)
					)
				} else {
					launchSuccessTextView.text = ctx.getString(R.string.unsuccessful)
					launchSuccessTextView.setTextColor(
						ContextCompat.getColor(
							itemView.context,
							R.color.colorUnsuccessful
						)
					)
				}
			} else {
				launchSuccessTextView.text = ctx.getString(R.string.no_data)
				launchSuccessTextView.setTextColor((ContextCompat.getColor(itemView.context, R.color.colorNoData)))
			}
		}
	}
}
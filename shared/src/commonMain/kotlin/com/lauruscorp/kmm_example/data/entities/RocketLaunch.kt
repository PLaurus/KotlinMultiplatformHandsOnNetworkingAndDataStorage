package com.lauruscorp.kmm_example.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketLaunch(
	@SerialName("flight_number")
	val flightNumber: Int,
	@SerialName("mission_name")
	val missionName: String,
	@SerialName("launch_year")
	val launchYear: Int,
	@SerialName("launch_date_utc")
	val launchDateUtc: String,
	@SerialName("rocket")
	val rocket: Rocket,
	@SerialName("details")
	val details: String?,
	@SerialName("launch_success")
	val launchSuccess: Boolean?,
	@SerialName("links")
	val links: Links
) {
	@Serializable
	data class Rocket(
		@SerialName("rocket_id")
		val id: Int,
		@SerialName("rocket_name")
		val name: String,
		@SerialName("rocket_type")
		val type: String
	)
	
	@Serializable
	data class Links(
		@SerialName("mission_patch")
		val missionPatchUrl: String?,
		@SerialName("article_link")
		val articleLink: String
	)
}
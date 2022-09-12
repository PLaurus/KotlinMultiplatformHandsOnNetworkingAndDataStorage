package com.lauruscorp.kmm_example.data.network

import com.lauruscorp.kmm_example.data.entities.RocketLaunch
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

internal class SpaceXApi {
	private val httpClient = HttpClient {
		install(JsonFeature) {
			val json = Json {
				ignoreUnknownKeys = true
			}
			serializer = KotlinxSerializer(json)
		}
	}
	
	suspend fun getAllLaunches(): List<RocketLaunch> {
		return httpClient.get(LAUNCHES_ENDPOINT)
	}
	
	companion object {
		private const val LAUNCHES_ENDPOINT = "https://api.spacexdata.com/v3/launches"
	}
}
package com.lauruscorp.kmm_example.data.repository

import com.lauruscorp.kmm_example.data.database.DatabaseDriverFactory
import com.lauruscorp.kmm_example.data.database.DatabaseFacade
import com.lauruscorp.kmm_example.data.entities.RocketLaunch
import com.lauruscorp.kmm_example.data.network.SpaceXApi

class RocketLaunchesRepository(databaseDriverFactory: DatabaseDriverFactory) {
	private val database = DatabaseFacade(databaseDriverFactory)
	private val api = SpaceXApi()
	@Throws(Exception::class)
	suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
		val cachedLaunches = database.getAllRocketLaunches()
		return if (cachedLaunches.isNotEmpty() && !forceReload) {
			cachedLaunches
		} else {
			api.getAllLaunches()
				.also {
					database.clearDatabase()
					database.createLaunches(it)
				}
		}
	}
}
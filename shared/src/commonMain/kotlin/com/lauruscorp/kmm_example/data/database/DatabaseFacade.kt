package com.lauruscorp.kmm_example.data.database

import com.lauruscorp.kmm_example.data.entities.RocketLaunch

internal class DatabaseFacade(
	databaseDriverFactory: DatabaseDriverFactory
) {
	private val database = AppDatabase(databaseDriverFactory.createDriver())
	private val dbQuery = database.appDatabaseQueries
	
	internal fun clearDatabase() {
		dbQuery.transaction {
			dbQuery.removeAllRockets()
			dbQuery.removeAllLaunches()
		}
	}
	
	internal fun getAllRocketLaunches(): List<RocketLaunch> {
		return dbQuery.selectAllLaunchesInfo(::mapRocketLaunchSelecting)
			.executeAsList()
	}
	
	internal fun createLaunches(
		launches: List<RocketLaunch>
	) {
		dbQuery.transaction {
			launches.forEach { launch ->
				val rocket = dbQuery.selectRocketById(launch.rocket.id)
					.executeAsOneOrNull()
				
				if (rocket == null) {
					insertRocket(launch)
				}
			}
		}
	}
	
	private fun mapRocketLaunchSelecting(
		flightNumber: Long,
		missionName: String,
		launchYear: Int,
		rocketId: String,
		details: String?,
		launchSuccess: Boolean?,
		launchDateUTC: String,
		missionPatchUrl: String?,
		articleUrl: String?,
		rocket_id: String?,
		name: String?,
		type: String?
	): RocketLaunch {
		return RocketLaunch(
			flightNumber = flightNumber.toInt(),
			missionName = missionName,
			launchYear = launchYear,
			details = details,
			launchDateUtc = launchDateUTC,
			launchSuccess = launchSuccess,
			rocket = RocketLaunch.Rocket(
				id = rocketId,
				name = name!!,
				type = type!!
			),
			links = RocketLaunch.Links(
				missionPatchUrl = missionPatchUrl,
				articleUrl = articleUrl
			)
		)
	}
	
	private fun insertRocket(launch: RocketLaunch) {
		dbQuery.insertRocket(
			id = launch.rocket.id,
			name = launch.rocket.name,
			type = launch.rocket.type
		)
	}
	
	private fun insertLaunch(launch: RocketLaunch) {
		dbQuery.insertLaunch(
			flightNumber = launch.flightNumber.toLong(),
			missionName = launch.missionName,
			launchYear = launch.launchYear,
			rocketId = launch.rocket.id,
			details = launch.details,
			launchSuccess = launch.launchSuccess ?: false,
			launchDateUtc = launch.launchDateUtc,
			missionPatchUrl = launch.links.missionPatchUrl,
			articleUrl = launch.links.articleUrl
		)
	}
}
package com.lauruscorp.kmm_example.data.database

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
	fun createDriver(): SqlDriver
}
package com.lauruscorp.kmm_example.data.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

internal actual class DatabaseDriverFactory {
	actual fun createDriver(): SqlDriver {
		return NativeSqliteDriver(AppDatabase.Schema, "test.db")
	}
}
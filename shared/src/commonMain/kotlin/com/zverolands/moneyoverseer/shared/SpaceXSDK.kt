package com.zverolands.moneyoverseer.shared

import com.zverolands.moneyoverseer.shared.cache.Database
import com.zverolands.moneyoverseer.shared.cache.DatabaseDriverFactory
import com.zverolands.moneyoverseer.shared.entity.RocketLaunch
import com.zverolands.moneyoverseer.shared.network.SpaceXApi


class SpaceXSDK (databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}

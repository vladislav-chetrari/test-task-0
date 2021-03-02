package com.example.testtask0.data.network.api.client

import com.example.testtask0.data.network.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsClient @Inject constructor(private val api: Api) {

    fun locations(page: Int) = api.locations(page).body
}
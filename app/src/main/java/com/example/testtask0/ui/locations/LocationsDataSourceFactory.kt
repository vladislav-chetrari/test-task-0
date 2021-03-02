package com.example.testtask0.ui.locations

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.testtask0.data.network.api.client.LocationsClient
import com.example.testtask0.ui.model.Location
import kotlin.coroutines.CoroutineContext

class LocationsDataSourceFactory(
    private val coroutineContext: CoroutineContext,
    private val client: LocationsClient
) : DataSource.Factory<Int, Location>() {

    val dataSource = MutableLiveData<LocationsDataSource>()

    override fun create(): DataSource<Int, Location> {
        val dataSource = LocationsDataSource(coroutineContext, client)
        this.dataSource.postValue(dataSource)
        return dataSource
    }
}
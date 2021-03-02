package com.example.testtask0.ui.locations

import com.example.testtask0.data.network.api.client.LocationsClient
import com.example.testtask0.ui.base.BasePageKeyedDataSource
import com.example.testtask0.ui.model.Location
import kotlin.coroutines.CoroutineContext

class LocationsDataSource(
    coroutineContext: CoroutineContext,
    private val client: LocationsClient
) : BasePageKeyedDataSource<Int, Location>(coroutineContext) {

    override suspend fun loadInitialPageData(
        params: LoadInitialParams<Int>
    ) = client.locations(1)

    override suspend fun loadNextPageData(
        params: LoadParams<Int>
    ) = client.locations(params.key)

    override fun onInitialPageResult(
        result: List<Location>,
        callback: LoadInitialCallback<Int, Location>
    ) = callback.onResult(result, null, 2)

    override fun onNextPageResult(
        result: List<Location>,
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Location>
    ) = callback.onResult(result, params.key + 1)
}
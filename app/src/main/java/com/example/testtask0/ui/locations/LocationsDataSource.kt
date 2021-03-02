package com.example.testtask0.ui.locations

import com.example.testtask0.data.network.api.client.LocationsClient
import com.example.testtask0.data.network.api.response.LocationResponse
import com.example.testtask0.ui.base.BasePageKeyedDataSource
import kotlin.coroutines.CoroutineContext

class LocationsDataSource(
    coroutineContext: CoroutineContext,
    private val client: LocationsClient
) : BasePageKeyedDataSource<Int, LocationResponse>(coroutineContext) {

    override suspend fun loadInitialPageData(
        params: LoadInitialParams<Int>
    ) = client.locations(1).locations

    override suspend fun loadNextPageData(
        params: LoadParams<Int>
    ) = client.locations(params.key).locations

    override fun onInitialPageResult(
        result: List<LocationResponse>,
        callback: LoadInitialCallback<Int, LocationResponse>
    ) = callback.onResult(result, null, 2)

    override fun onNextPageResult(
        result: List<LocationResponse>,
        params: LoadParams<Int>,
        callback: LoadCallback<Int, LocationResponse>
    ) = callback.onResult(result, params.key + 1)
}
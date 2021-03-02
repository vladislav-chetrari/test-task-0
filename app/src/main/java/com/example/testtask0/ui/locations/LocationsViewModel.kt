package com.example.testtask0.ui.locations

import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.testtask0.data.network.api.client.LocationsClient
import com.example.testtask0.ui.base.BaseViewModel
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    client: LocationsClient
) : BaseViewModel() {

    private val dataSourceFactory = LocationsDataSourceFactory(viewModelScope.coroutineContext, client)
    private val dataSource = dataSourceFactory.dataSource
    val locations = LivePagedListBuilder(dataSourceFactory, pagingConfig).build()
    val initialProgress = dataSource.switchMap(LocationsDataSource::initialProgress)
    val pagingProgress = dataSource.switchMap(LocationsDataSource::pagingProgress)
    val error = dataSource.switchMap(LocationsDataSource::error)

    fun onRefresh() {
        dataSourceFactory.dataSource.value?.invalidate()
    }

    fun onRetry() {
        dataSourceFactory.dataSource.value?.retry()
    }

    private companion object {
        const val PAGE_SIZE = 10
        val pagingConfig = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()
    }
}
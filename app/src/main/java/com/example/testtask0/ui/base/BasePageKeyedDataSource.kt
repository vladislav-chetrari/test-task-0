package com.example.testtask0.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BasePageKeyedDataSource<Key, ListItem>(
    override val coroutineContext: CoroutineContext
) : PageKeyedDataSource<Key, ListItem>(), CoroutineScope {

    val pagingProgress = MutableLiveData(false)
    val initialProgress = MutableLiveData(false)
    val error = MutableLiveData<Throwable>()

    private var retry: (() -> Unit)? = null

    protected abstract suspend fun loadInitialPageData(params: LoadInitialParams<Key>): List<ListItem>

    protected abstract fun onInitialPageResult(
        result: List<ListItem>,
        callback: LoadInitialCallback<Key, ListItem>
    )

    protected abstract suspend fun loadNextPageData(params: LoadParams<Key>): List<ListItem>

    protected abstract fun onNextPageResult(
        result: List<ListItem>,
        params: LoadParams<Key>,
        callback: LoadCallback<Key, ListItem>
    )

    override fun loadInitial(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Key, ListItem>
    ) = load(initialProgress) {
        val result = withContext(Dispatchers.IO) { loadInitialPageData(params) }
        onInitialPageResult(result, callback)
    }

    override fun loadAfter(
        params: LoadParams<Key>,
        callback: LoadCallback<Key, ListItem>
    ) = load(pagingProgress) {
        val result = withContext(Dispatchers.IO) { loadNextPageData(params) }
        onNextPageResult(result, params, callback)
    }

    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<Key, ListItem>) = Unit

    fun retry() {
        retry?.invoke()
    }

    private fun load(progress: MutableLiveData<Boolean>, dataFetch: suspend () -> Unit) {
        launch {
            progress.postValue(true)
            try {
                withContext(Dispatchers.IO) { dataFetch() }
            } catch (t: Throwable) {
                error.postValue(t)
                retry = { load(progress, dataFetch) }
            } finally {
                progress.postValue(false)
            }
        }
    }
}
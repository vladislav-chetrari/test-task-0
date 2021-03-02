package com.example.testtask0.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.testtask0.ui.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel : ViewModel() {

    protected fun <T> resultLiveData(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        block: suspend () -> T
    ): LiveData<Result<T>> = liveData(viewModelScope.coroutineContext + dispatcher) {
        emit(Result.Progress)
        try {
            val value = block.invoke()
            emit(Result.Complete.Success(value))
        } catch (t: Throwable) {
            emit(Result.Complete.Error(t))
        }
    }
}
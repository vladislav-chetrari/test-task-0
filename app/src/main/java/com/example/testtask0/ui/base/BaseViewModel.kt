package com.example.testtask0.ui.base

import androidx.lifecycle.*
import com.example.testtask0.ui.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel : ViewModel() {

    protected val <T> LiveData<T>.mutable: MutableLiveData<T>
        get() = this as MutableLiveData<T>

    protected val <T> LiveData<T>.mediator: MediatorLiveData<T>
        get() = this as MediatorLiveData<T>

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

    protected fun <T, R> LiveData<Result<T>>.mapResult(f: (T) -> R): LiveData<Result<R>> = map { result ->
        when (result) {
            Result.Progress -> Result.Progress
            is Result.Complete.Error -> result
            is Result.Complete.Success -> Result.Complete.Success(f(result.value))
        }
    }
}